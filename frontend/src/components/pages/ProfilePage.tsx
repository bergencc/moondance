import { useQuery } from '@tanstack/react-query';
import { Link } from 'react-router-dom';
import {
    Mail,
    GraduationCap,
    Calendar,
    FileText,
    Download,
    Eye,
    Star,
    Settings,
    LogOut
} from 'lucide-react';
import { api } from '@/services/api';
import { useAuth } from '@/context/AuthContext';
import NoteCard from '@/components/notes/NoteCard';
import type { Note, ApiResponse, PageResponse } from '@/types';

export default function ProfilePage() {
    const { user, logout } = useAuth();

    const { data: myNotes } = useQuery({
        queryKey: ['my-notes'],
        queryFn: async () => {
            const res = await api.get<ApiResponse<PageResponse<Note>>>('/notes/my-notes?size=20');

            return res.data.data;
        },
    });

    if (!user) {
        return null;
    }

    const totalViews = myNotes?.content.reduce((acc, note) => acc + note.viewCount, 0) || 0;
    const totalDownloads = myNotes?.content.reduce((acc, note) => acc + note.downloadCount, 0) || 0;
    const avgRating = myNotes?.content.length
        ? myNotes.content.reduce((acc, note) => acc + note.averageRating, 0) / myNotes.content.length
        : 0;

    return (
        <div className="min-h-[calc(100vh-4rem)] py-8">
            <div className="max-w-5xl mx-auto px-6">
                {/* Profile Header */}
                <div className="cyber-card rounded-lg p-8 mb-8">
                    <div className="flex flex-col md:flex-row gap-6 items-start md:items-center justify-between">
                        <div className="flex items-center gap-6">
                            {/* Avatar */}
                            <div className="w-24 h-24 rounded-full bg-linear-to-br from-neon-cyan to-neon-magenta p-0.5">
                                <div className="w-full h-full rounded-full bg-card flex items-center justify-center">
                                    {user.avatarUrl ? (
                                        <img
                                            src={user.avatarUrl}
                                            alt={user.name}
                                            className="w-full h-full rounded-full object-cover"
                                        />
                                    ) : (
                                        <span className="font-display text-3xl text-neon-cyan">
                      {user.name.charAt(0).toUpperCase()}
                    </span>
                                    )}
                                </div>
                            </div>

                            {/* Info */}
                            <div>
                                <h1 className="font-display text-2xl font-bold mb-1">{user.name}</h1>
                                <div className="flex flex-col gap-1 text-muted-foreground text-sm">
                                    <div className="flex items-center gap-2">
                                        <Mail className="w-4 h-4" />
                                        {user.email}
                                    </div>
                                    {user.major && (
                                        <div className="flex items-center gap-2">
                                            <GraduationCap className="w-4 h-4" />
                                            {user.major}
                                        </div>
                                    )}
                                    {user.graduationYear && (
                                        <div className="flex items-center gap-2">
                                            <Calendar className="w-4 h-4" />
                                            Class of {user.graduationYear}
                                        </div>
                                    )}
                                </div>
                            </div>
                        </div>

                        {/* Actions */}
                        <div className="flex gap-3">
                            <button className="flex items-center gap-2 px-4 py-2 border border-border rounded-lg hover:border-neon-cyan/50 transition-colors">
                                <Settings className="w-4 h-4" />
                                Settings
                            </button>
                            <button
                                onClick={logout}
                                className="flex items-center gap-2 px-4 py-2 border border-border rounded-lg hover:border-red-500/50 hover:text-red-500 transition-colors"
                            >
                                <LogOut className="w-4 h-4" />
                                Sign Out
                            </button>
                        </div>
                    </div>

                    {/* Stats */}
                    <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mt-8 pt-8 border-t border-border/50">
                        <div className="text-center">
                            <div className="font-display text-2xl font-bold text-neon-cyan">
                                {myNotes?.totalElements || 0}
                            </div>
                            <div className="text-sm text-muted-foreground flex items-center justify-center gap-1">
                                <FileText className="w-4 h-4" />
                                Notes Shared
                            </div>
                        </div>
                        <div className="text-center">
                            <div className="font-display text-2xl font-bold text-neon-magenta">
                                {totalViews}
                            </div>
                            <div className="text-sm text-muted-foreground flex items-center justify-center gap-1">
                                <Eye className="w-4 h-4" />
                                Total Views
                            </div>
                        </div>
                        <div className="text-center">
                            <div className="font-display text-2xl font-bold text-neon-purple">
                                {totalDownloads}
                            </div>
                            <div className="text-sm text-muted-foreground flex items-center justify-center gap-1">
                                <Download className="w-4 h-4" />
                                Downloads
                            </div>
                        </div>
                        <div className="text-center">
                            <div className="font-display text-2xl font-bold text-neon-yellow">
                                {avgRating.toFixed(1)}
                            </div>
                            <div className="text-sm text-muted-foreground flex items-center justify-center gap-1">
                                <Star className="w-4 h-4" />
                                Avg Rating
                            </div>
                        </div>
                    </div>

                    {/* Reputation */}
                    <div className="mt-6 p-4 bg-muted rounded-lg">
                        <div className="flex items-center justify-between mb-2">
                            <span className="text-sm font-medium">Reputation Points</span>
                            <span className="font-mono text-neon-cyan">{user.reputationPoints}</span>
                        </div>
                        <div className="h-2 bg-background rounded-full overflow-hidden">
                            <div
                                className="h-full bg-linear-to-r from-neon-cyan to-neon-magenta transition-all"
                                style={{ width: `${Math.min(user.reputationPoints / 10, 100)}%` }}
                            />
                        </div>
                        <p className="text-xs text-muted-foreground mt-2">
                            Earn points by sharing quality notes and receiving upvotes
                        </p>
                    </div>
                </div>

                {/* My Notes */}
                <div>
                    <div className="flex items-center justify-between mb-6">
                        <h2 className="font-display text-xl font-bold">
                            <FileText className="w-5 h-5 inline mr-2 text-neon-cyan" />
                            My Notes
                        </h2>
                        <Link
                            to="/upload"
                            className="text-neon-cyan hover:text-neon-cyan/80 text-sm transition-colors"
                        >
                            + Upload New
                        </Link>
                    </div>

                    {myNotes?.content.length === 0 ? (
                        <div className="cyber-card rounded-lg p-8 text-center">
                            <FileText className="w-12 h-12 mx-auto mb-3 text-muted-foreground" />
                            <h3 className="font-semibold mb-1">No notes yet</h3>
                            <p className="text-muted-foreground mb-4">Start sharing your knowledge with others!</p>
                            <Link
                                to="/upload"
                                className="inline-flex items-center gap-2 px-4 py-2 bg-neon-cyan text-cyber-black font-medium rounded-lg hover:shadow-neon-cyan transition-all"
                            >
                                Upload Your First Note
                            </Link>
                        </div>
                    ) : (
                        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
                            {myNotes?.content.map((note) => (
                                <NoteCard key={note.id} note={note} showActions />
                            ))}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}
