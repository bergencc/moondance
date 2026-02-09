import { useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useQuery, useMutation } from '@tanstack/react-query';
import { toast } from 'react-hot-toast';
import {
    Upload,
    X,
    Check,
    AlertCircle
} from 'lucide-react';
import { api } from '@/services/api';
import { useAuth } from '@/context/AuthContext';
import type { Course, Session, CourseSession, ApiResponse, NoteType } from '@/types';
import { NOTE_TYPE_LABELS } from '@/types';

export default function UploadPage() {
    const navigate = useNavigate();
    const { user } = useAuth();
    const schoolId = user?.schoolId || 1;

    const [file, setFile] = useState<File | null>(null);
    const [dragActive, setDragActive] = useState(false);
    const [formData, setFormData] = useState({
        title: '',
        description: '',
        type: 'LECTURE_NOTES' as NoteType,
        courseId: '',
        sessionId: '',
        courseSessionId: '',
        weekLabel: '',
        tags: '',
    });

    const { data: courses } = useQuery({
        queryKey: ['courses', schoolId],
        queryFn: async () => {
            const res = await api.get<ApiResponse<Course[]>>(`/courses?schoolId=${schoolId}`);

            return res.data.data;
        },
    });

    const { data: sessions } = useQuery({
        queryKey: ['sessions', schoolId],
        queryFn: async () => {
            const res = await api.get<ApiResponse<Session[]>>(`/sessions?schoolId=${schoolId}`);

            return res.data.data;
        },
    });

    const { data: courseSessions } = useQuery({
        queryKey: ['course-sessions', formData.courseId],
        queryFn: async () => {
            const res = await api.get<ApiResponse<CourseSession[]>>(`/course-sessions?courseId=${formData.courseId}`);

            return res.data.data;
        },
        enabled: !!formData.courseId,
    });

    const uploadMutation = useMutation({
        mutationFn: async (data: FormData) => {
            const res = await api.post('/notes', data, {
                headers: { 'Content-Type': 'multipart/form-data' },
            });

            return res.data;
        },
        onSuccess: (data) => {
            toast.success('Note uploaded successfully!');
            navigate(`/note/${data.data.id}`);
        },
        onError: (error: any) => {
            toast.error(error.response?.data?.message || 'Upload failed');
        },
    });

    const handleDrag = useCallback((e: React.DragEvent) => {
        e.preventDefault();
        e.stopPropagation();

        if (e.type === 'dragenter' || e.type === 'dragover') {
            setDragActive(true);
        } else if (e.type === 'dragleave') {
            setDragActive(false);
        }
    }, []);

    const handleDrop = useCallback((e: React.DragEvent) => {
        e.preventDefault();
        e.stopPropagation();

        setDragActive(false);

        if (e.dataTransfer.files && e.dataTransfer.files[0]) {
            setFile(e.dataTransfer.files[0]);
        }

    }, []);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files[0]) {
            setFile(e.target.files[0]);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        const { name, value } = e.target;

        setFormData(prev => ({ ...prev, [name]: value }));

        // Reset course session when course changes
        if (name === 'courseId') {
            setFormData(prev => ({ ...prev, courseSessionId: '' }));
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!file) {
            toast.error('Please select a file');

            return;
        }

        if (!formData.courseSessionId) {
            toast.error('Please select a course and session');

            return;
        }

        const data = new FormData();
        data.append('file', file);

        const noteData = {
            title: formData.title,
            description: formData.description || undefined,
            type: formData.type,
            courseSessionId: parseInt(formData.courseSessionId),
            weekLabel: formData.weekLabel || undefined,
            tags: formData.tags ? formData.tags.split(',').map(t => t.trim()).filter(Boolean) : undefined,
        };

        data.append('data', new Blob([JSON.stringify(noteData)], { type: 'application/json' }));

        uploadMutation.mutate(data);
    };

    const noteTypes: NoteType[] = [
        'LECTURE_NOTES',
        'EXAM_PREP',
        'CHEAT_SHEET',
        'SUMMARY',
        'LAB_GUIDE',
        'CODING_EXAMPLES',
        'PAST_EXAM',
        'OTHER',
    ];

    return (
        <div className="min-h-[calc(100vh-4rem)] py-8">
            <div className="max-w-2xl mx-auto px-6">
                <div className="text-center mb-8">
                    <h1 className="font-display text-3xl font-bold mb-2">
                        Upload <span className="text-neon-cyan">Notes</span>
                    </h1>
                    <p className="text-muted-foreground">Share your knowledge with fellow students</p>
                </div>

                <form onSubmit={handleSubmit} className="space-y-6">
                    {/* File Upload */}
                    <div
                        className={`relative border-2 border-dashed rounded-lg p-8 text-center transition-colors ${
                            dragActive
                                ? 'border-neon-cyan bg-neon-cyan/5'
                                : file
                                    ? 'border-neon-green bg-neon-green/5'
                                    : 'border-border hover:border-neon-cyan/50'
                        }`}
                        onDragEnter={handleDrag}
                        onDragLeave={handleDrag}
                        onDragOver={handleDrag}
                        onDrop={handleDrop}
                    >
                        <input
                            type="file"
                            onChange={handleFileChange}
                            accept=".pdf,.doc,.docx,.ppt,.pptx,.jpg,.jpeg,.png,.gif"
                            className="absolute inset-0 w-full h-full opacity-0 cursor-pointer"
                        />

                        {file ? (
                            <div className="flex items-center justify-center gap-3">
                                <div className="w-12 h-12 rounded-lg bg-neon-green/10 flex items-center justify-center">
                                    <Check className="w-6 h-6 text-neon-green" />
                                </div>
                                <div className="text-left">
                                    <p className="font-medium truncate max-w-xs">{file.name}</p>
                                    <p className="text-sm text-muted-foreground">
                                        {(file.size / (1024 * 1024)).toFixed(2)} MB
                                    </p>
                                </div>
                                <button
                                    type="button"
                                    onClick={(e) => { e.stopPropagation(); setFile(null); }}
                                    className="p-2 hover:bg-muted rounded-lg transition-colors"
                                >
                                    <X className="w-5 h-5" />
                                </button>
                            </div>
                        ) : (
                            <>
                                <Upload className="w-12 h-12 mx-auto mb-4 text-muted-foreground" />
                                <p className="font-medium mb-1">Drop your file here or click to browse</p>
                                <p className="text-sm text-muted-foreground">
                                    PDF, Word, PowerPoint, or images up to 50MB
                                </p>
                            </>
                        )}
                    </div>

                    {/* Title */}
                    <div>
                        <label className="block text-sm font-medium mb-2">Title *</label>
                        <input
                            type="text"
                            name="title"
                            value={formData.title}
                            onChange={handleChange}
                            className="cyber-input w-full px-4 py-3 rounded-lg bg-muted"
                            placeholder="e.g., Week 5 Lecture Notes - Data Structures"
                            required
                        />
                    </div>

                    {/* Description */}
                    <div>
                        <label className="block text-sm font-medium mb-2">Description</label>
                        <textarea
                            name="description"
                            value={formData.description}
                            onChange={handleChange}
                            rows={3}
                            className="cyber-input w-full px-4 py-3 rounded-lg bg-muted resize-none"
                            placeholder="Brief description of what's covered..."
                        />
                    </div>

                    {/* Note Type */}
                    <div>
                        <label className="block text-sm font-medium mb-2">Type *</label>
                        <div className="grid grid-cols-2 sm:grid-cols-4 gap-2">
                            {noteTypes.map((type) => (
                                <button
                                    key={type}
                                    type="button"
                                    onClick={() => setFormData(prev => ({ ...prev, type }))}
                                    className={`px-3 py-2 text-sm rounded-lg border transition-colors ${
                                        formData.type === type
                                            ? 'border-neon-cyan bg-neon-cyan/10 text-neon-cyan'
                                            : 'border-border hover:border-neon-cyan/50'
                                    }`}
                                >
                                    {NOTE_TYPE_LABELS[type]}
                                </button>
                            ))}
                        </div>
                    </div>

                    {/* Course Selection */}
                    <div className="grid sm:grid-cols-2 gap-4">
                        <div>
                            <label className="block text-sm font-medium mb-2">Course *</label>
                            <select
                                name="courseId"
                                value={formData.courseId}
                                onChange={handleChange}
                                className="cyber-input w-full px-4 py-3 rounded-lg bg-muted appearance-none cursor-pointer"
                                required
                            >
                                <option value="">Select course</option>
                                {courses?.map((course) => (
                                    <option key={course.id} value={course.id}>
                                        {course.code} - {course.title}
                                    </option>
                                ))}
                            </select>
                        </div>

                        <div>
                            <label className="block text-sm font-medium mb-2">Session *</label>
                            <select
                                name="courseSessionId"
                                value={formData.courseSessionId}
                                onChange={handleChange}
                                className="cyber-input w-full px-4 py-3 rounded-lg bg-muted appearance-none cursor-pointer"
                                required
                                disabled={!formData.courseId}
                            >
                                <option value="">Select session</option>
                                {courseSessions?.map((cs) => (
                                    <option key={cs.id} value={cs.id}>
                                        {cs.sessionName} {cs.instructorName && `- ${cs.instructorName}`}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>

                    {/* Week Label */}
                    <div>
                        <label className="block text-sm font-medium mb-2">Week (optional)</label>
                        <input
                            type="text"
                            name="weekLabel"
                            value={formData.weekLabel}
                            onChange={handleChange}
                            className="cyber-input w-full px-4 py-3 rounded-lg bg-muted"
                            placeholder="e.g., Week 5, Midterm, Finals"
                        />
                    </div>

                    {/* Tags */}
                    <div>
                        <label className="block text-sm font-medium mb-2">Tags (optional)</label>
                        <input
                            type="text"
                            name="tags"
                            value={formData.tags}
                            onChange={handleChange}
                            className="cyber-input w-full px-4 py-3 rounded-lg bg-muted"
                            placeholder="e.g., algorithms, sorting, recursion (comma separated)"
                        />
                    </div>

                    {/* Submit */}
                    <button
                        type="submit"
                        disabled={uploadMutation.isPending || !file}
                        className="cyber-button w-full bg-neon-cyan text-cyber-black font-semibold py-4 rounded-lg hover:shadow-neon-cyan transition-all disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                        {uploadMutation.isPending ? (
                            <span className="flex items-center justify-center gap-2">
                <div className="w-5 h-5 border-2 border-cyber-black border-t-transparent rounded-full animate-spin" />
                Uploading...
              </span>
                        ) : (
                            <span className="flex items-center justify-center gap-2">
                <Upload className="w-5 h-5" />
                Upload Note
              </span>
                        )}
                    </button>

                    {/* Guidelines */}
                    <div className="cyber-card rounded-lg p-4">
                        <div className="flex items-start gap-3">
                            <AlertCircle className="w-5 h-5 text-neon-yellow shrink-0 mt-0.5" />
                            <div className="text-sm text-muted-foreground">
                                <p className="font-medium text-foreground mb-1">Upload Guidelines</p>
                                <ul className="list-disc list-inside space-y-1">
                                    <li>Only upload content you have the right to share</li>
                                    <li>Do not upload copyrighted materials without permission</li>
                                    <li>Ensure notes are relevant to the selected course</li>
                                    <li>Use descriptive titles for better discoverability</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
}
