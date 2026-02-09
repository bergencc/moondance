import { Link } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import {
    FileText,
    Upload,
    Search,
    TrendingUp,
    Users,
    Zap,
    ArrowRight,
    BookOpen,
    Star
} from 'lucide-react';
import { api } from '@/services/api.ts';
import NoteCard from '@/components/notes/NoteCard.tsx';
import { useAuth } from '@/context/AuthContext.tsx';
import type { Note, ApiResponse, PageResponse } from '@/types';

export default function HomePage() {
    const { user, isAuthenticated } = useAuth();
    const schoolId = user?.schoolId || 1;

    const { data: trendingNotes } = useQuery({
        queryKey: ['trending-notes', schoolId],
        queryFn: async () => {
            const res = await api.get<ApiResponse<PageResponse<Note>>>(`/notes/trending?schoolId=${schoolId}&size=6`);

            return res.data.data.content;
        },
        enabled: !!schoolId,
    });

    const { data: recentNotes } = useQuery({
        queryKey: ['recent-notes', schoolId],
        queryFn: async () => {
            const res = await api.get<ApiResponse<PageResponse<Note>>>(`/notes/recent?schoolId=${schoolId}&size=6`);

            return res.data.data.content;
        },
        enabled: !!schoolId,
    });

    return (
        <div className="relative">
            {/* Hero Section */}
            <section className="relative min-h-[80vh] flex items-center justify-center overflow-hidden">
                {/* Background Effects */}
                <div className="absolute inset-0 cyber-grid opacity-50" />
                <div className="absolute inset-0 bg-linear-to-b from-transparent via-background/50 to-background" />

                {/* Animated orbs */}
                <div className="absolute top-1/4 left-1/4 w-96 h-96 bg-neon-cyan/10 rounded-full blur-3xl animate-float" />
                <div className="absolute bottom-1/4 right-1/4 w-96 h-96 bg-neon-magenta/10 rounded-full blur-3xl animate-float" style={{ animationDelay: '-3s' }} />

                <div className="relative z-10 max-w-6xl mx-auto px-6 text-center">
                    <div className="animate-slideUp">
                        <h1 className="font-display text-5xl md:text-7xl font-bold mb-6">
                            <span className="gradient-text">MOON DANCE</span>
                        </h1>
                        <p className="text-xl md:text-2xl text-muted-foreground max-w-2xl mx-auto mb-4">
                            The future of collaborative learning
                        </p>
                        <p className="text-lg text-muted-foreground/80 max-w-xl mx-auto mb-12">
                            Share notes, ace exams, build your academic network.
                        </p>
                    </div>

                    <div className="flex flex-col sm:flex-row gap-4 justify-center animate-slideUp stagger-2">
                        <Link
                            to="/browse"
                            className="cyber-button bg-neon-cyan text-cyber-black font-semibold text-lg px-8 py-4 inline-flex items-center justify-center gap-2 hover:shadow-neon-cyan transition-all"
                        >
                            <Search className="w-5 h-5" />
                            Browse Notes
                        </Link>
                        {isAuthenticated ? (
                            <Link
                                to="/upload"
                                className="cyber-button bg-transparent border-2 border-neon-magenta text-neon-magenta font-semibold text-lg px-8 py-4 inline-flex items-center justify-center gap-2 hover:bg-neon-magenta/10 transition-all"
                            >
                                <Upload className="w-5 h-5" />
                                Upload Notes
                            </Link>
                        ) : (
                            <Link
                                to="/register"
                                className="cyber-button bg-transparent border-2 border-neon-magenta text-neon-magenta font-semibold text-lg px-8 py-4 inline-flex items-center justify-center gap-2 hover:bg-neon-magenta/10 transition-all"
                            >
                                Get Started
                                <ArrowRight className="w-5 h-5" />
                            </Link>
                        )}
                    </div>
                </div>
            </section>

            {/* Stats Section */}
            <section className="py-16 border-y border-border/50">
                <div className="max-w-6xl mx-auto px-6">
                    <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
                        {[
                            { icon: FileText, label: 'Notes Shared', value: '200+' },
                            { icon: Users, label: 'Active Students', value: '50+' },
                            { icon: BookOpen, label: 'Courses Covered', value: '20+' },
                            { icon: Star, label: 'Avg Rating', value: '4.8' },
                        ].map((stat, idx) => (
                            <div key={idx} className="text-center group">
                                <div className="w-14 h-14 mx-auto mb-4 rounded-lg bg-muted flex items-center justify-center group-hover:bg-neon-cyan/10 transition-colors">
                                    <stat.icon className="w-7 h-7 text-neon-cyan" />
                                </div>
                                <div className="font-display text-3xl font-bold text-foreground mb-1">
                                    {stat.value}
                                </div>
                                <div className="text-sm text-muted-foreground">{stat.label}</div>
                            </div>
                        ))}
                    </div>
                </div>
            </section>

            {/* Features Section */}
            <section className="py-20">
                <div className="max-w-6xl mx-auto px-6">
                    <h2 className="font-display text-3xl md:text-4xl font-bold text-center mb-4">
                        <span className="text-neon-cyan">Features</span> that matter
                    </h2>
                    <p className="text-muted-foreground text-center max-w-xl mx-auto mb-16">
                        Built for students, by students. Everything you need to succeed academically.
                    </p>

                    <div className="grid md:grid-cols-3 gap-8">
                        {[
                            {
                                icon: Search,
                                title: 'Smart Search',
                                description: 'Find notes instantly with full-text search across titles, descriptions, and content.',
                                color: 'neon-cyan',
                            },
                            {
                                icon: TrendingUp,
                                title: 'Trending Content',
                                description: 'Discover the most helpful notes based on downloads and ratings from your peers.',
                                color: 'neon-magenta',
                            },
                            {
                                icon: Zap,
                                title: 'Instant Access',
                                description: 'Preview PDFs in-browser and download files with a single click. No barriers.',
                                color: 'neon-purple',
                            },
                        ].map((feature, idx) => (
                            <div
                                key={idx}
                                className="cyber-card p-6 rounded-lg hover:border-neon-cyan/50 transition-all group"
                            >
                                <div className={`w-12 h-12 rounded-lg bg-${feature.color}/10 flex items-center justify-center mb-4 group-hover:shadow-${feature.color} transition-shadow`}>
                                    <feature.icon className={`w-6 h-6 text-${feature.color}`} />
                                </div>
                                <h3 className="font-display text-xl font-semibold mb-2">{feature.title}</h3>
                                <p className="text-muted-foreground">{feature.description}</p>
                            </div>
                        ))}
                    </div>
                </div>
            </section>

            {/* Trending Notes Section */}
            {trendingNotes && trendingNotes.length > 0 && (
                <section className="py-20 border-t border-border/50">
                    <div className="max-w-6xl mx-auto px-6">
                        <div className="flex items-center justify-between mb-8">
                            <div>
                                <h2 className="font-display text-2xl md:text-3xl font-bold mb-2">
                                    <TrendingUp className="w-6 h-6 inline mr-2 text-neon-magenta" />
                                    Trending Notes
                                </h2>
                                <p className="text-muted-foreground">Most popular notes this week</p>
                            </div>
                            <Link
                                to="/browse?sort=trending"
                                className="text-neon-cyan hover:text-neon-cyan/80 flex items-center gap-1 transition-colors"
                            >
                                View all <ArrowRight className="w-4 h-4" />
                            </Link>
                        </div>

                        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
                            {trendingNotes.map((note) => (
                                <NoteCard key={note.id} note={note} />
                            ))}
                        </div>
                    </div>
                </section>
            )}

            {/* Recent Notes Section */}
            {recentNotes && recentNotes.length > 0 && (
                <section className="py-20 border-t border-border/50">
                    <div className="max-w-6xl mx-auto px-6">
                        <div className="flex items-center justify-between mb-8">
                            <div>
                                <h2 className="font-display text-2xl md:text-3xl font-bold mb-2">
                                    <FileText className="w-6 h-6 inline mr-2 text-neon-cyan" />
                                    Recent Uploads
                                </h2>
                                <p className="text-muted-foreground">Fresh notes from the community</p>
                            </div>
                            <Link
                                to="/browse?sort=recent"
                                className="text-neon-cyan hover:text-neon-cyan/80 flex items-center gap-1 transition-colors"
                            >
                                View all <ArrowRight className="w-4 h-4" />
                            </Link>
                        </div>

                        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
                            {recentNotes.map((note) => (
                                <NoteCard key={note.id} note={note} />
                            ))}
                        </div>
                    </div>
                </section>
            )}

            {/* CTA Section */}
            {!isAuthenticated && (
                <section className="py-20 border-t border-border/50">
                    <div className="max-w-4xl mx-auto px-6 text-center">
                        <h2 className="font-display text-3xl md:text-4xl font-bold mb-4">
                            Ready to <span className="gradient-text">level up</span> your grades?
                        </h2>
                        <p className="text-lg text-muted-foreground mb-8">
                            Join thousands of students sharing knowledge and achieving academic excellence.
                        </p>
                        <Link
                            to="/register"
                            className="cyber-button bg-neon-cyan text-cyber-black font-semibold text-lg px-10 py-4 inline-flex items-center gap-2 hover:shadow-neon-cyan transition-all"
                        >
                            Create Free Account
                            <ArrowRight className="w-5 h-5" />
                        </Link>
                    </div>
                </section>
            )}
        </div>
    );
}
