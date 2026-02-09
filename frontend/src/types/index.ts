export interface ApiResponse<T> {
    success: boolean;
    message?: string;
    data: T;
    timestamp: string;
}

export interface PageResponse<T> {
    content: T[];
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
    first: boolean;
    last: boolean;
}

export interface User {
    id: number;
    email: string;
    name: string;
    major?: string;
    graduationYear?: number;
    avatarUrl?: string;
    role: 'STUDENT' | 'MODERATOR' | 'ADMIN';
    reputationPoints: number;
    emailVerified: boolean;
    schoolId?: number;
    schoolName?: string;
    createdAt: string;
}

export interface AuthResponse {
    accessToken: string;
    refreshToken: string;
    tokenType: string;
    expiresIn: number;
    user: User;
}

export interface School {
    id: number;
    name: string;
    domain: string;
    logoUrl?: string;
    city?: string;
    state?: string;
    country?: string;
}

export interface Department {
    id: number;
    code: string;
    name: string;
    description?: string;
    schoolId: number;
    courseCount: number;
}

export interface Course {
    id: number;
    code: string;
    title: string;
    description?: string;
    credits?: number;
    departmentId: number;
    departmentName: string;
}

export interface Session {
    id: number;
    name: string;
    type: 'FALL' | 'SPRING' | 'SUMMER' | 'WINTER' | 'YEAR_ROUND';
    year: number;
    startDate?: string;
    endDate?: string;
}

export interface Instructor {
    id: number;
    name: string;
    email?: string;
    title?: string;
    departmentId: number;
    departmentName: string;
}

export interface CourseSession {
    id: number;
    courseId: number;
    courseCode: string;
    courseTitle: string;
    sessionId: number;
    sessionName: string;
    instructorId?: number;
    instructorName?: string;
    section?: string;
    noteCount?: number;
    totalDownloads?: number;
}

export type NoteType =
    | 'LECTURE_NOTES'
    | 'EXAM_PREP'
    | 'CHEAT_SHEET'
    | 'SUMMARY'
    | 'LAB_GUIDE'
    | 'CODING_EXAMPLES'
    | 'PAST_EXAM'
    | 'OTHER';

export const NOTE_TYPE_LABELS: Record<NoteType, string> = {
    LECTURE_NOTES: 'Lecture Notes',
    EXAM_PREP: 'Exam Prep',
    CHEAT_SHEET: 'Cheat Sheet',
    SUMMARY: 'Summary',
    LAB_GUIDE: 'Lab Guide',
    CODING_EXAMPLES: 'Coding Examples',
    PAST_EXAM: 'Past Exam',
    OTHER: 'Other',
};

export const NOTE_TYPE_COLORS: Record<NoteType, string> = {
    LECTURE_NOTES: 'bg-neon-cyan/20 text-neon-cyan',
    EXAM_PREP: 'bg-neon-magenta/20 text-neon-magenta',
    CHEAT_SHEET: 'bg-neon-green/20 text-neon-green',
    SUMMARY: 'bg-neon-yellow/20 text-neon-yellow',
    LAB_GUIDE: 'bg-neon-purple/20 text-neon-purple',
    CODING_EXAMPLES: 'bg-neon-blue/20 text-neon-blue',
    PAST_EXAM: 'bg-neon-orange/20 text-neon-orange',
    OTHER: 'bg-muted text-muted-foreground',
};

export type ProcessingStatus = 'PENDING' | 'PROCESSING' | 'READY' | 'FAILED';

export interface Note {
    id: number;
    title: string;
    description?: string;
    type: NoteType;
    fileKey: string;
    fileSize: number;
    mimeType: string;
    originalFileName?: string;
    thumbnailUrl?: string;
    processingStatus: ProcessingStatus;
    weekLabel?: string;
    courseSessionId: number;
    courseCode: string;
    courseTitle: string;
    sessionName: string;
    instructorName?: string;
    uploaderId: number;
    uploaderName: string;
    uploaderAvatarUrl?: string;
    tags: string[];
    viewCount: number;
    downloadCount: number;
    averageRating: number;
    voteCount: number;
    createdAt: string;
    updatedAt: string;
}

export interface CreateNoteRequest {
    title: string;
    description?: string;
    type: NoteType;
    courseSessionId: number;
    weekLabel?: string;
    tags?: string[];
}

export interface Vote {
    id: number;
    noteId: number;
    userId: number;
    value: number;
    rating?: number;
    createdAt: string;
}

export type ReportReason =
    | 'SPAM'
    | 'COPYRIGHT'
    | 'WRONG_COURSE'
    | 'INAPPROPRIATE'
    | 'LOW_QUALITY'
    | 'OTHER';

export type ReportStatus = 'PENDING' | 'REVIEWED' | 'RESOLVED' | 'DISMISSED';

export interface Report {
    id: number;
    noteId: number;
    noteTitle: string;
    reporterId: number;
    reporterName: string;
    reason: ReportReason;
    description?: string;
    status: ReportStatus;
    moderatorNotes?: string;
    reviewedAt?: string;
    createdAt: string;
}

export interface Tag {
    id: number;
    name: string;
    color?: string;
}

export interface LoginForm {
    email: string;
    password: string;
}

export interface RegisterForm {
    email: string;
    password: string;
    name: string;
    major?: string;
    graduationYear?: number;
    inviteCode?: string;
}
