import { Link } from 'react-router-dom';
import { Calendar, Download, Star, FileText, Eye, Trash2, Edit } from 'lucide-react';
import type { Note } from '@/types';
import { NOTE_TYPE_LABELS, NOTE_TYPE_COLORS } from '@/types';
import { formatDistanceToNow } from '@/utils/date.ts';

interface NoteCardProps {
    note: Note;
    variant?: 'grid' | 'list';
    showActions?: boolean;
}

export default function NoteCard({ note, variant = 'grid', showActions = false }: NoteCardProps) {
    const formatFileSize = (bytes: number) => {
        if (bytes < 1024) return `${bytes} B`;

        if (bytes < 1024 * 1024) return `${(bytes / 1024).toFixed(1)} KB`;

        return `${(bytes / (1024 * 1024)).toFixed(1)} MB`;
    };

    if (variant === 'list') {
        return (
            <Link to={`/note/${note.id}`}>
                <div className="cyber-card rounded-lg p-4 hover:border-neon-cyan/50 transition-all flex items-center gap-4">
                    <div className="w-16 h-16 rounded-lg bg-muted flex items-center justify-center shrink-0">
                        <FileText className="w-8 h-8 text-muted-foreground" />
                    </div>
                    <div className="flex-1 min-w-0">
                        <div className="flex items-center gap-2 mb-1">
                            <span className="text-xs font-mono text-neon-cyan">{note.courseCode}</span>
                            <span className={`px-1.5 py-0.5 text-[10px] rounded ${NOTE_TYPE_COLORS[note.type]}`}>
                {NOTE_TYPE_LABELS[note.type]}
              </span>
                        </div>
                        <h3 className="font-semibold truncate">{note.title}</h3>
                        <div className="flex items-center gap-4 mt-1 text-xs text-muted-foreground">
                            <span>{note.uploaderName}</span>
                            <span className="flex items-center gap-1">
                <Download className="w-3 h-3" />
                                {note.downloadCount}
              </span>
                            <span>{formatDistanceToNow(new Date(note.createdAt))} ago</span>
                        </div>
                    </div>
                    {note.averageRating > 0 && (
                        <div className="flex items-center gap-1 text-neon-yellow">
                            <Star className="w-4 h-4 fill-current" />
                            <span className="font-mono text-sm">{note.averageRating.toFixed(1)}</span>
                        </div>
                    )}
                </div>
            </Link>
        );
    }

    return (
        <div className="cyber-card rounded-lg overflow-hidden hover:border-neon-cyan/50 transition-all group">
            <Link to={`/note/${note.id}`}>
                {/* Preview */}
                <div className="relative aspect-4/3 bg-linear-to-br from-muted to-muted/50">
                    {/* Rating badge */}
                    {note.averageRating > 0 && (
                        <div className="absolute top-3 right-3 flex items-center gap-1 bg-background/90 backdrop-blur-sm px-2 py-1 rounded text-sm font-medium">
                            <Star className="w-4 h-4 text-neon-yellow fill-neon-yellow" />
                            <span>{note.averageRating.toFixed(1)}</span>
                        </div>
                    )}

                    {/* Type badge */}
                    <div className="absolute top-3 left-3">
            <span className={`px-2 py-0.5 text-[10px] rounded uppercase tracking-wider ${NOTE_TYPE_COLORS[note.type]}`}>
              {NOTE_TYPE_LABELS[note.type].split(' ')[0]}
            </span>
                    </div>

                    {/* Preview content */}
                    <div className="absolute inset-0 flex items-center justify-center">
                        <div className="text-center text-muted-foreground">
                            <FileText className="w-12 h-12 mx-auto mb-2 opacity-30" />
                            <span className="text-xs uppercase tracking-wider opacity-50">
                {note.mimeType.split('/')[1].toUpperCase()}
              </span>
                        </div>
                    </div>

                    {/* Hover overlay */}
                    <div className="absolute inset-0 bg-linear-to-t from-cyber-black/80 via-transparent to-transparent opacity-0 group-hover:opacity-100 transition-opacity">
                        <div className="absolute bottom-3 left-3">
                            <span className="text-xs text-neon-cyan">Click to view</span>
                        </div>
                    </div>
                </div>

                {/* Content */}
                <div className="p-4">
                    {/* Course code */}
                    <div className="text-xs font-mono text-neon-cyan mb-1">{note.courseCode}</div>

                    {/* Title */}
                    <h3 className="font-semibold line-clamp-2 mb-2 group-hover:text-neon-cyan transition-colors">
                        {note.title}
                    </h3>

                    {/* Meta */}
                    <div className="flex items-center gap-3 text-xs text-muted-foreground mb-3">
            <span className="flex items-center gap-1">
              <Calendar className="w-3 h-3" />
                {formatDistanceToNow(new Date(note.createdAt))}
            </span>
                        <span className="flex items-center gap-1">
              <Download className="w-3 h-3" />
                            {note.downloadCount}
            </span>
                        <span className="flex items-center gap-1">
              <Eye className="w-3 h-3" />
                            {note.viewCount}
            </span>
                    </div>

                    {/* Footer */}
                    <div className="flex items-center justify-between pt-3 border-t border-border/50">
                        <div className="flex items-center gap-2">
                            <div className="w-6 h-6 rounded-full bg-linear-to-br from-neon-cyan to-neon-magenta flex items-center justify-center text-[10px] font-bold text-cyber-black">
                                {note.uploaderName.charAt(0).toUpperCase()}
                            </div>
                            <span className="text-sm text-muted-foreground truncate max-w-25">
                {note.uploaderName}
              </span>
                        </div>
                        <span className="text-xs text-muted-foreground font-mono">
              {formatFileSize(note.fileSize)}
            </span>
                    </div>
                </div>
            </Link>

            {/* Actions for profile page */}
            {showActions && (
                <div className="px-4 pb-4 pt-0 flex gap-2">
                    <button className="flex-1 flex items-center justify-center gap-1 py-1.5 text-xs border border-border rounded hover:border-neon-cyan/50 transition-colors">
                        <Edit className="w-3 h-3" />
                        Edit
                    </button>
                    <button className="flex-1 flex items-center justify-center gap-1 py-1.5 text-xs border border-border rounded hover:border-red-500/50 hover:text-red-500 transition-colors">
                        <Trash2 className="w-3 h-3" />
                        Delete
                    </button>
                </div>
            )}
        </div>
    );
}
