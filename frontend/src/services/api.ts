import axios, { AxiosError, type InternalAxiosRequestConfig } from 'axios';

const API_URL = import.meta.env.VITE_API_URL;

export const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Request interceptor to add auth token
api.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        const token = localStorage.getItem('accessToken');

        if (token && config.headers) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },
    (error) => Promise.reject(error)
);

// Response interceptor for error handling and token refresh
api.interceptors.response.use(
    (response) => response,
    async (error: AxiosError) => {
        const originalRequest = error.config as InternalAxiosRequestConfig & { _retry?: boolean };

        // Handle 401 errors (token expired)
        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            const refreshToken = localStorage.getItem('refreshToken');

            if (refreshToken) {
                try {
                    const response = await axios.post(`${API_URL}/auth/refresh`, {
                        refreshToken,
                    });

                    const { accessToken, refreshToken: newRefreshToken } = response.data.data;

                    localStorage.setItem('accessToken', accessToken);
                    localStorage.setItem('refreshToken', newRefreshToken);

                    if (originalRequest.headers) {
                        originalRequest.headers.Authorization = `Bearer ${accessToken}`;
                    }

                    return api(originalRequest);
                } catch (refreshError) {
                    localStorage.removeItem('accessToken');
                    localStorage.removeItem('refreshToken');
                    localStorage.removeItem('user');

                    window.location.href = '/login';

                    return Promise.reject(refreshError);
                }
            }
        }

        return Promise.reject(error);
    }
);

// Auth API
export const authApi = {
    login: (email: string, password: string) =>
        api.post('/auth/login', { email, password }),

    register: (data: {
        email: string;
        password: string;
        name: string;
        major?: string;
        graduationYear?: number;
        inviteCode?: string;
    }) => api.post('/auth/register', data),

    refresh: (refreshToken: string) =>
        api.post('/auth/refresh', { refreshToken }),

    changePassword: (currentPassword: string, newPassword: string) =>
        api.post('/auth/change-password', { currentPassword, newPassword }),
};

// User API
export const userApi = {
    getMe: () => api.get('/users/me'),
    getUser: (id: number) => api.get(`/users/${id}`),
    updateProfile: (data: { name?: string; major?: string; graduationYear?: number; avatarUrl?: string }) =>
        api.patch('/users/me', data),
};

// School & Course API
export const schoolApi = {
    getAll: () => api.get('/schools'),
    getById: (id: number) => api.get(`/schools/${id}`),
};

export const departmentApi = {
    getBySchool: (schoolId: number) => api.get('/departments', { params: { schoolId } }),
    getById: (id: number) => api.get(`/departments/${id}`),
};

export const courseApi = {
    getByDepartment: (departmentId: number) => api.get('/courses', { params: { departmentId } }),
    getBySchool: (schoolId: number) => api.get('/courses', { params: { schoolId } }),
    search: (schoolId: number, query: string, page = 0, size = 20) =>
        api.get('/courses/search', { params: { schoolId, query, page, size } }),
    getById: (id: number) => api.get(`/courses/${id}`),
};

export const sessionApi = {
    getBySchool: (schoolId: number) => api.get('/sessions', { params: { schoolId } }),
};

export const instructorApi = {
    getBySchool: (schoolId: number) => api.get('/instructors', { params: { schoolId } }),
    search: (schoolId: number, query: string) =>
        api.get('/instructors/search', { params: { schoolId, query } }),
};

export const courseSessionApi = {
    getByCourse: (courseId: number) => api.get('/course-sessions', { params: { courseId } }),
    getById: (id: number) => api.get(`/course-sessions/${id}`),
};

// Notes API
export const noteApi = {
    upload: (formData: FormData) =>
        api.post('/notes', formData, {
            headers: { 'Content-Type': 'multipart/form-data' },
        }),

    getById: (id: number) => api.get(`/notes/${id}`),

    getByCourseSession: (courseSessionId: number, page = 0, size = 20) =>
        api.get(`/notes/course-session/${courseSessionId}`, { params: { page, size } }),

    search: (schoolId: number, query: string, page = 0, size = 20) =>
        api.get('/notes/search', { params: { schoolId, query, page, size } }),

    getTrending: (schoolId: number, page = 0, size = 20) =>
        api.get('/notes/trending', { params: { schoolId, page, size } }),

    getRecent: (schoolId: number, page = 0, size = 20) =>
        api.get('/notes/recent', { params: { schoolId, page, size } }),

    getByType: (schoolId: number, type: string, page = 0, size = 20) =>
        api.get('/notes/by-type', { params: { schoolId, type, page, size } }),

    getMyNotes: (page = 0, size = 20) =>
        api.get('/notes/my-notes', { params: { page, size } }),

    update: (id: number, data: { title?: string; description?: string; type?: string; tags?: string[] }) =>
        api.patch(`/notes/${id}`, data),

    delete: (id: number) => api.delete(`/notes/${id}`),

    getDownloadUrl: (id: number) => api.get(`/notes/${id}/download`),

    getViewUrl: (id: number) => api.get(`/notes/${id}/view`),
};

// Votes & Reports API
export const voteApi = {
    vote: (noteId: number, value: number, rating?: number) =>
        api.post(`/notes/${noteId}/vote`, { value, rating }),

    removeVote: (noteId: number) => api.delete(`/notes/${noteId}/vote`),

    getMyVote: (noteId: number) => api.get(`/notes/${noteId}/my-vote`),
};

export const reportApi = {
    create: (noteId: number, reason: string, description?: string) =>
        api.post(`/notes/${noteId}/report`, { reason, description }),

    getPending: (schoolId: number, page = 0, size = 20) =>
        api.get('/reports/pending', { params: { schoolId, page, size } }),

    review: (reportId: number, status: string, moderatorNotes?: string) =>
        api.patch(`/reports/${reportId}/review`, { status, moderatorNotes }),
};

// Tags API
export const tagApi = {
    getAll: () => api.get('/tags'),
    search: (query: string) => api.get('/tags/search', { params: { query } }),
    getPopular: (schoolId: number) => api.get('/tags/popular', { params: { schoolId } }),
};

export default api;
