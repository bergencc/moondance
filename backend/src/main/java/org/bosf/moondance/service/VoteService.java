package org.bosf.moondance.service;

import org.bosf.moondance.dto.InteractionDto;
import org.bosf.moondance.entity.Note;
import org.bosf.moondance.entity.User;
import org.bosf.moondance.entity.Vote;
import org.bosf.moondance.exception.ApiException;
import org.bosf.moondance.repository.NoteRepository;
import org.bosf.moondance.repository.UserRepository;
import org.bosf.moondance.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteService {

    private static final Logger log = LoggerFactory.getLogger(VoteService.class);

    private final VoteRepository voteRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, NoteRepository noteRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public InteractionDto.VoteResponse vote(Long noteId, Long userId, InteractionDto.VoteRequest request) {
        Note note = noteRepository.findActiveById(noteId)
                .orElseThrow(() -> new ApiException.NotFoundException("Note", noteId));

        User user = userRepository.findActiveById(userId)
                .orElseThrow(() -> new ApiException.NotFoundException("User", userId));

        Vote vote = voteRepository.findByNoteIdAndUserId(noteId, userId)
                .orElse(Vote.builder()
                        .note(note)
                        .user(user)
                        .build());

        vote.setValue(request.getValue());

        if (request.getRating() != null) {
            vote.setRating(request.getRating());
        }

        vote = voteRepository.save(vote);

        updateNoteStats(note);

        log.info("Vote recorded: note={}, user={}, value={}", noteId, userId, request.getValue());

        return InteractionDto.VoteResponse.fromEntity(vote);
    }

    @Transactional
    public void removeVote(Long noteId, Long userId) {
        Vote vote = voteRepository.findByNoteIdAndUserId(noteId, userId)
                .orElseThrow(() -> new ApiException.NotFoundException("Vote not found"));

        Note note = vote.getNote();
        
        voteRepository.delete(vote);

        updateNoteStats(note);

        log.info("Vote removed: note={}, user={}", noteId, userId);
    }

    @Transactional(readOnly = true)
    public InteractionDto.VoteResponse getUserVote(Long noteId, Long userId) {
        return voteRepository.findByNoteIdAndUserId(noteId, userId)
                .map(InteractionDto.VoteResponse::fromEntity)
                .orElse(null);
    }

    private void updateNoteStats(Note note) {
        Long positiveCount = voteRepository.countPositiveByNoteId(note.getId());
        Long negativeCount = voteRepository.countNegativeByNoteId(note.getId());
        Double avgRating = voteRepository.averageRatingByNoteId(note.getId());
        Long ratingCount = voteRepository.countRatingsByNoteId(note.getId());

        note.setVoteCount((int) (positiveCount - negativeCount));
        note.setAverageRating(avgRating != null ? avgRating : 0.0);

        noteRepository.save(note);
    }
}
