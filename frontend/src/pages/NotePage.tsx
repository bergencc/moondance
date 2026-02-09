import { useParams, Link } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { toast } from 'react-hot-toast';
import {
    Download,
    Eye,
    Star,
    ChevronRight,
    Calendar,
    User,
    FileText,
    ThumbsUp,
    ThumbsDown,
    Flag,
    ExternalLink,
    Clock
} from 'lucide-react';
import { api } from '@/services/api.ts';
import { useAuth } from '@/context/AuthContext.tsx';
import { formatDistanceToNow } from '@/utils/date.ts';
import type { Note, Vote, ApiResponse } from '@/types';
import { NOTE_TYPE_LABELS, NOTE_TYPE_COLORS } from '@/types';

export default function NotePage() {
    const { noteId } = useParams<{ noteId: string }>();
    const { user, isAuthenticated } = useAuth();
    const queryClient = useQueryClient();

    const { data: note, isLoading } = useQuery({
        queryKey: ['note', noteId],
        queryFn: async () => {
            const res = await api.get<ApiResponse<Note>>(`/notes/${noteId}`);

            return res.data.data;
        },
    });

    const { data: userVote } = useQuery({
        queryKey: ['vote', noteId, user?.id],
        queryFn: async () => {
            const res = await api.get<ApiResponse<Vote>>(`/notes/${noteId}/votes/me`);

            return res.data.data;
        },
        enabled: isAuthenticated,
    });

    const voteMutation = useMutation({
        mutationFn: async (value: number) => {
            await api.post(`/notes/${noteId}/votes`, { value });
        },
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['note', noteId] });
            queryClient.invalidateQueries({ queryKey: ['vote', noteId] });
        },
    });

    const handleDownload = async () => {
        try {
            const res = await api.get<ApiResponse<string>>(`/notes/${noteId}/download`);

            window.open(res.data.data, '_blank');

            queryClient.invalidateQueries({ queryKey: ['note', noteId] });
        } catch (error) {
            toast.error('Failed to download file');
        }
    };

    const handleView = async () => {
        try {
            const res = await api.get<ApiResponse<string>>(`/notes/${noteId}/view`);

            window.open(res.data.data, '_blank');
        } catch (error) {
            toast.error('Failed to open file');
        }
    };

    const handleVote = (value: number) => {
        if (!isAuthenticated) {
            toast.error('Please sign in to vote');

            return;
        }
        voteMutation.mutate(value);
    };

    if (isLoading) {
        return (
            <div className="min-h-[calc(100vh-4rem)] flex items-center justify-center">
                <div className="animate-spin w-8 h-8 border-2 border-neon-cyan border-t-transparent rounded-full" />
            </div>
        );
    }

    if (!note) {
        return (
            <div className="min-h-[calc(100vh-4rem)] flex items-center justify-center">
                <div className="text-center">
                    <FileText className="w-16 h-16 mx-auto mb-4 text-muted-foreground" />
                    <h2 className="text-xl font-semibold mb-2">Note not found</h2>
                    <Link to="/browse" className="text-neon-cyan hover:text-neon-cyan/80">
                        Browse all notes
                    </Link>
                </div>
            </div>
        );
    }

    const formatFileSize = (bytes: number) => {
        if (bytes < 1024) return `${bytes} B`;

        if (bytes < 1024 * 1024) return `${(bytes / 1024).toFixed(1)} KB`;

        return `${(bytes / (1024 * 1024)).toFixed(1)} MB`;
    };

    return (
        <div className="min-h-[calc(100vh-4rem)] py-8">
            <div className="max-w-4xl mx-auto px-6">
                {/* Breadcrumb */}
                <nav className="flex items-center gap-2 text-sm text-muted-foreground mb-6">
                    <Link to="/browse" className="hover:text-foreground transition-colors">Browse</Link>
                    <ChevronRight className="w-4 h-4" />
                    <Link to={`/course/${note.courseSessionId}`} className="hover:text-foreground transition-colors">
                        {note.courseCode}
                    </Link>
                    <ChevronRight className="w-4 h-4" />
                    <span className="text-foreground truncate max-w-xs">{note.title}</span>
                </nav>

                {/* Main Content */}
                <div className="cyber-card rounded-lg p-8 mb-6">
                    {/* Header */}
                    <div className="flex flex-wrap items-start justify-between gap-4 mb-6">
                        <div>
                            <div className="flex flex-wrap items-center gap-2 mb-3">
                <span className="px-3 py-1 bg-neon-cyan/10 text-neon-cyan font-mono text-sm rounded">
                  {note.courseCode}
                </span>
                                <span className={`px-2 py-0.5 text-xs rounded ${NOTE_TYPE_COLORS[note.type]}`}>
                  {NOTE_TYPE_LABELS[note.type]}
                </span>
                                {note.processingStatus === 'PROCESSING' && (
                                    <span className="px-2 py-0.5 text-xs rounded bg-yellow-500/20 text-yellow-500">
                    Processing...
                  </span>
                                )}
                            </div>
                            <h1 className="font-display text-2xl md:text-3xl font-bold mb-2">{note.title}</h1>
                            <p className="text-muted-foreground">{note.courseTitle}</p>
                        </div>

                        {/* Vote Controls */}
                        <div className="flex items-center gap-2">
                            <button
                                onClick={() => handleVote(1)}
                                className={`p-2 rounded-lg border transition-colors ${
                                    userVote?.value === 1
                                        ? 'border-neon-green bg-neon-green/10 text-neon-green'
                                        : 'border-border hover:border-neon-green/50'
                                }`}
                            >
                                <ThumbsUp className="w-5 h-5" />
                            </button>
                            <span className={`font-mono font-bold ${
                                note.voteCount > 0 ? 'text-neon-green' : note.voteCount < 0 ? 'text-red-500' : ''
                            }`}>
                {note.voteCount}
              </span>
                            <button
                                onClick={() => handleVote(-1)}
                                className={`p-2 rounded-lg border transition-colors ${
                                    userVote?.value === -1
                                        ? 'border-red-500 bg-red-500/10 text-red-500'
                                        : 'border-border hover:border-red-500/50'
                                }`}
                            >
                                <ThumbsDown className="w-5 h-5" />
                            </button>
                        </div>
                    </div>

                    {/* Description */}
                    {note.description && (
                        <p className="text-muted-foreground mb-6">{note.description}</p>
                    )}

                    {/* Tags */}
                    {note.tags.length > 0 && (
                        <div className="flex flex-wrap gap-2 mb-6">
                            {note.tags.map((tag) => (
                                <span key={tag} className="px-2 py-1 bg-muted text-sm rounded">
                  #{tag}
                </span>
                            ))}
                        </div>
                    )}

                    {/* Meta Info */}
                    <div className="grid sm:grid-cols-2 gap-4 mb-8">
                        <div className="flex items-center gap-3 text-muted-foreground">
                            <User className="w-5 h-5" />
                            <span>Uploaded by <span className="text-foreground">{note.uploaderName}</span></span>
                        </div>
                        <div className="flex items-center gap-3 text-muted-foreground">
                            <Calendar className="w-5 h-5" />
                            <span>{note.sessionName}</span>
                        </div>
                        <div className="flex items-center gap-3 text-muted-foreground">
                            <Clock className="w-5 h-5" />
                            <span>{formatDistanceToNow(new Date(note.createdAt))} ago</span>
                        </div>
                        <div className="flex items-center gap-3 text-muted-foreground">
                            <FileText className="w-5 h-5" />
                            <span>{formatFileSize(note.fileSize)} • {note.mimeType.split('/')[1].toUpperCase()}</span>
                        </div>
                    </div>

                    {/* Stats */}
                    <div className="flex flex-wrap gap-6 mb-8 pb-8 border-b border-border/50">
                        <div className="flex items-center gap-2">
                            <Eye className="w-5 h-5 text-neon-cyan" />
                            <span className="font-mono">{note.viewCount}</span>
                            <span className="text-muted-foreground">views</span>
                        </div>
                        <div className="flex items-center gap-2">
                            <Download className="w-5 h-5 text-neon-magenta" />
                            <span className="font-mono">{note.downloadCount}</span>
                            <span className="text-muted-foreground">downloads</span>
                        </div>
                        {note.averageRating > 0 && (
                            <div className="flex items-center gap-2">
                                <Star className="w-5 h-5 text-neon-yellow fill-neon-yellow" />
                                <span className="font-mono">{note.averageRating.toFixed(1)}</span>
                                <span className="text-muted-foreground">rating</span>
                            </div>
                        )}
                    </div>

                    {/* Action Buttons */}
                    <div className="flex flex-wrap gap-4">
                        <button
                            onClick={handleDownload}
                            className="cyber-button flex items-center gap-2 px-6 py-3 bg-neon-cyan text-cyber-black font-semibold rounded-lg hover:shadow-neon-cyan transition-all"
                        >
                            <Download className="w-5 h-5" />
                            Download
                        </button>
                        <button
                            onClick={handleView}
                            className="cyber-button flex items-center gap-2 px-6 py-3 border border-border hover:border-neon-cyan/50 rounded-lg transition-all"
                        >
                            <ExternalLink className="w-5 h-5" />
                            View in Browser
                        </button>
                        {isAuthenticated && (
                            <button className="cyber-button flex items-center gap-2 px-6 py-3 border border-border hover:border-red-500/50 text-muted-foreground hover:text-red-500 rounded-lg transition-all">
                                <Flag className="w-5 h-5" />
                                Report
                            </button>
                        )}
                    </div>
                </div>

                {/* Related Notes Placeholder */}
                <div className="cyber-card rounded-lg p-6">
                    <h2 className="font-semibold mb-4">More from {note.courseCode}</h2>
                    <p className="text-muted-foreground text-sm">
                        Browse more notes from this course to find additional study materials.
                    </p>
                    <Link
                        to={`/course/${note.courseSessionId}`}
                        className="inline-flex items-center gap-1 mt-4 text-neon-cyan hover:text-neon-cyan/80 transition-colors"
                    >
                        View all notes <ChevronRight className="w-4 h-4" />
                    </Link>
                </div>
            </div>
        </div>
    );
}
