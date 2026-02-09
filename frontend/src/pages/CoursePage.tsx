import { useParams, Link } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import {
    BookOpen,
    Users,
    Download,
    ChevronRight,
    Calendar,
    FileText
} from 'lucide-react';
import { api } from '@/services/api.ts';
import NoteCard from '@/components/notes/NoteCard.tsx';
import type { Course, CourseSession, Note, ApiResponse, PageResponse } from '@/types';

export default function CoursePage() {
    const { courseId } = useParams<{ courseId: string }>();

    const { data: course, isLoading: courseLoading } = useQuery({
        queryKey: ['course', courseId],
        queryFn: async () => {
            const res = await api.get<ApiResponse<Course>>(`/courses/${courseId}`);

            return res.data.data;
        },
    });

    const { data: sessions } = useQuery({
        queryKey: ['course-sessions', courseId],
        queryFn: async () => {
            const res = await api.get<ApiResponse<CourseSession[]>>(`/course-sessions?courseId=${courseId}`);

            return res.data.data;
        },
    });

    const selectedSessionId = sessions?.[0]?.id;

    const { data: notes, isLoading: notesLoading } = useQuery({
        queryKey: ['course-notes', selectedSessionId],
        queryFn: async () => {
            const res = await api.get<ApiResponse<PageResponse<Note>>>(`/notes/course-session/${selectedSessionId}?size=20`);

            return res.data.data;
        },
        enabled: !!selectedSessionId,
    });

    if (courseLoading) {
        return (
            <div className="min-h-[calc(100vh-4rem)] flex items-center justify-center">
                <div className="animate-spin w-8 h-8 border-2 border-neon-cyan border-t-transparent rounded-full" />
            </div>
        );
    }

    if (!course) {
        return (
            <div className="min-h-[calc(100vh-4rem)] flex items-center justify-center">
                <div className="text-center">
                    <BookOpen className="w-16 h-16 mx-auto mb-4 text-muted-foreground" />
                    <h2 className="text-xl font-semibold mb-2">Course not found</h2>
                    <Link to="/browse" className="text-neon-cyan hover:text-neon-cyan/80">
                        Browse all courses
                    </Link>
                </div>
            </div>
        );
    }

    const totalDownloads = sessions?.reduce((acc, s) => acc + (s.totalDownloads || 0), 0) || 0;
    const totalNotes = sessions?.reduce((acc, s) => acc + (s.noteCount || 0), 0) || 0;

    return (
        <div className="min-h-[calc(100vh-4rem)] py-8">
            <div className="max-w-6xl mx-auto px-6">
                {/* Breadcrumb */}
                <nav className="flex items-center gap-2 text-sm text-muted-foreground mb-6">
                    <Link to="/browse" className="hover:text-foreground transition-colors">Browse</Link>
                    <ChevronRight className="w-4 h-4" />
                    <span className="text-foreground">{course.code}</span>
                </nav>

                {/* Course Header */}
                <div className="cyber-card rounded-lg p-8 mb-8">
                    <div className="flex flex-col md:flex-row gap-6 justify-between">
                        <div>
                            <div className="flex items-center gap-3 mb-3">
                <span className="px-3 py-1 bg-neon-cyan/10 text-neon-cyan font-mono text-sm rounded">
                  {course.code}
                </span>
                                {course.credits && (
                                    <span className="text-muted-foreground text-sm">{course.credits} credits</span>
                                )}
                            </div>
                            <h1 className="font-display text-3xl font-bold mb-2">{course.title}</h1>
                            <p className="text-muted-foreground">{course.departmentName}</p>
                            {course.description && (
                                <p className="mt-4 text-muted-foreground max-w-2xl">{course.description}</p>
                            )}
                        </div>

                        {/* Stats */}
                        <div className="flex gap-6 md:gap-8">
                            <div className="text-center">
                                <div className="font-display text-2xl font-bold text-neon-cyan">{totalNotes}</div>
                                <div className="text-sm text-muted-foreground">Notes</div>
                            </div>
                            <div className="text-center">
                                <div className="font-display text-2xl font-bold text-neon-magenta">{totalDownloads}</div>
                                <div className="text-sm text-muted-foreground">Downloads</div>
                            </div>
                        </div>
                    </div>
                </div>

                {/* Sessions */}
                {sessions && sessions.length > 0 && (
                    <div className="mb-8">
                        <h2 className="font-semibold mb-4 flex items-center gap-2">
                            <Calendar className="w-5 h-5 text-neon-cyan" />
                            Available Sessions
                        </h2>
                        <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-4">
                            {sessions.map((session) => (
                                <div
                                    key={session.id}
                                    className="cyber-card rounded-lg p-4 hover:border-neon-cyan/50 transition-colors cursor-pointer"
                                >
                                    <div className="font-medium mb-1">{session.sessionName}</div>
                                    {session.instructorName && (
                                        <div className="text-sm text-muted-foreground mb-2">
                                            <Users className="w-4 h-4 inline mr-1" />
                                            {session.instructorName}
                                        </div>
                                    )}
                                    <div className="flex items-center gap-4 text-sm text-muted-foreground">
                    <span className="flex items-center gap-1">
                      <FileText className="w-4 h-4" />
                        {session.noteCount || 0} notes
                    </span>
                                        <span className="flex items-center gap-1">
                      <Download className="w-4 h-4" />
                                            {session.totalDownloads || 0}
                    </span>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                )}

                {/* Notes */}
                <div>
                    <h2 className="font-semibold mb-4 flex items-center gap-2">
                        <FileText className="w-5 h-5 text-neon-cyan" />
                        Course Notes
                    </h2>

                    {notesLoading ? (
                        <div className="flex items-center justify-center py-12">
                            <div className="animate-spin w-8 h-8 border-2 border-neon-cyan border-t-transparent rounded-full" />
                        </div>
                    ) : notes?.content.length === 0 ? (
                        <div className="text-center py-12 cyber-card rounded-lg">
                            <FileText className="w-12 h-12 mx-auto mb-3 text-muted-foreground" />
                            <h3 className="font-semibold mb-1">No notes yet</h3>
                            <p className="text-muted-foreground mb-4">Be the first to share notes for this course!</p>
                            <Link
                                to="/upload"
                                className="inline-flex items-center gap-2 px-4 py-2 bg-neon-cyan text-cyber-black font-medium rounded-lg hover:shadow-neon-cyan transition-all"
                            >
                                Upload Notes
                            </Link>
                        </div>
                    ) : (
                        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
                            {notes?.content.map((note) => (
                                <NoteCard key={note.id} note={note} />
                            ))}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}
