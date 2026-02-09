import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useSearchParams, Link } from 'react-router-dom';
import {
    Filter,
    Grid,
    List,
    ChevronDown,
    Folder,
    BookOpen
} from 'lucide-react';
import { api } from '@/services/api.ts';
import NoteCard from '@/components/notes/NoteCard.tsx';
import { useAuth } from '@/context/AuthContext.tsx';
import type { Department, Note, ApiResponse, PageResponse } from '@/types';

export default function BrowsePage() {
    const [searchParams, setSearchParams] = useSearchParams();
    const [viewMode, setViewMode] = useState<'grid' | 'list'>('grid');
    const [showFilters, setShowFilters] = useState(false);
    const { user } = useAuth();
    const schoolId = user?.schoolId || 1;

    const sort = searchParams.get('sort') || 'recent';
    const departmentId = searchParams.get('department');
    const noteType = searchParams.get('type');
    const page = parseInt(searchParams.get('page') || '0');

    const { data: departments } = useQuery({
        queryKey: ['departments', schoolId],
        queryFn: async () => {
            const res = await api.get<ApiResponse<Department[]>>(`/departments?schoolId=${schoolId}`);

            return res.data.data;
        },
    });

    const { data: notesData, isLoading } = useQuery({
        queryKey: ['notes', schoolId, sort, departmentId, noteType, page],
        queryFn: async () => {
            let url = sort === 'trending'
                ? `/notes/trending?schoolId=${schoolId}&page=${page}&size=12`
                : `/notes/recent?schoolId=${schoolId}&page=${page}&size=12`;

            if (noteType) {
                url = `/notes/by-type?schoolId=${schoolId}&type=${noteType}&page=${page}&size=12`;
            }

            const res = await api.get<ApiResponse<PageResponse<Note>>>(url);

            return res.data.data;
        },
    });

    const updateFilter = (key: string, value: string | null) => {
        const newParams = new URLSearchParams(searchParams);

        if (value) {
            newParams.set(key, value);
        } else {
            newParams.delete(key);
        }

        newParams.set('page', '0');

        setSearchParams(newParams);
    };

    const noteTypes = [
        { value: 'LECTURE_NOTES', label: 'Lecture Notes' },
        { value: 'EXAM_PREP', label: 'Exam Prep' },
        { value: 'CHEAT_SHEET', label: 'Cheat Sheet' },
        { value: 'SUMMARY', label: 'Summary' },
        { value: 'LAB_GUIDE', label: 'Lab Guide' },
        { value: 'CODING_EXAMPLES', label: 'Coding Examples' },
        { value: 'PAST_EXAM', label: 'Past Exam' },
    ];

    return (
        <div className="min-h-[calc(100vh-4rem)] py-8">
            <div className="max-w-7xl mx-auto px-6">
                {/* Header */}
                <div className="mb-8">
                    <h1 className="font-display text-3xl font-bold mb-2">
                        Browse <span className="text-neon-cyan">Notes</span>
                    </h1>
                    <p className="text-muted-foreground">
                        Discover study materials shared by students
                    </p>
                </div>

                {/* Toolbar */}
                <div className="flex flex-col sm:flex-row gap-4 justify-between items-start sm:items-center mb-8">
                    <div className="flex items-center gap-4">
                        {/* Sort Dropdown */}
                        <div className="relative">
                            <select
                                value={sort}
                                onChange={(e) => updateFilter('sort', e.target.value)}
                                className="cyber-input appearance-none pl-4 pr-10 py-2 rounded-lg bg-muted cursor-pointer"
                            >
                                <option value="recent">Most Recent</option>
                                <option value="trending">Trending</option>
                            </select>
                            <ChevronDown className="absolute right-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground pointer-events-none" />
                        </div>

                        {/* Filter Button */}
                        <button
                            onClick={() => setShowFilters(!showFilters)}
                            className={`flex items-center gap-2 px-4 py-2 rounded-lg border transition-colors ${
                                showFilters ? 'border-neon-cyan text-neon-cyan' : 'border-border hover:border-neon-cyan/50'
                            }`}
                        >
                            <Filter className="w-4 h-4" />
                            Filters
                        </button>
                    </div>

                    {/* View Mode Toggle */}
                    <div className="flex items-center gap-1 bg-muted rounded-lg p-1">
                        <button
                            onClick={() => setViewMode('grid')}
                            className={`p-2 rounded-md transition-colors ${
                                viewMode === 'grid' ? 'bg-background text-neon-cyan' : 'text-muted-foreground hover:text-foreground'
                            }`}
                        >
                            <Grid className="w-4 h-4" />
                        </button>
                        <button
                            onClick={() => setViewMode('list')}
                            className={`p-2 rounded-md transition-colors ${
                                viewMode === 'list' ? 'bg-background text-neon-cyan' : 'text-muted-foreground hover:text-foreground'
                            }`}
                        >
                            <List className="w-4 h-4" />
                        </button>
                    </div>
                </div>

                {/* Filters Panel */}
                {showFilters && (
                    <div className="cyber-card rounded-lg p-6 mb-8 animate-slideUp">
                        <div className="grid md:grid-cols-2 gap-6">
                            {/* Note Type Filter */}
                            <div>
                                <label className="block text-sm font-medium mb-3">Note Type</label>
                                <div className="flex flex-wrap gap-2">
                                    <button
                                        onClick={() => updateFilter('type', null)}
                                        className={`px-3 py-1.5 rounded-md text-sm transition-colors ${
                                            !noteType
                                                ? 'bg-neon-cyan text-cyber-black'
                                                : 'bg-muted hover:bg-muted/80'
                                        }`}
                                    >
                                        All Types
                                    </button>
                                    {noteTypes.map((type) => (
                                        <button
                                            key={type.value}
                                            onClick={() => updateFilter('type', type.value)}
                                            className={`px-3 py-1.5 rounded-md text-sm transition-colors ${
                                                noteType === type.value
                                                    ? 'bg-neon-cyan text-cyber-black'
                                                    : 'bg-muted hover:bg-muted/80'
                                            }`}
                                        >
                                            {type.label}
                                        </button>
                                    ))}
                                </div>
                            </div>

                            {/* Department Filter */}
                            <div>
                                <label className="block text-sm font-medium mb-3">Department</label>
                                <select
                                    value={departmentId || ''}
                                    onChange={(e) => updateFilter('department', e.target.value || null)}
                                    className="cyber-input w-full px-4 py-2 rounded-lg bg-muted appearance-none cursor-pointer"
                                >
                                    <option value="">All Departments</option>
                                    {departments?.map((dept) => (
                                        <option key={dept.id} value={dept.id}>
                                            {dept.code} - {dept.name}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>
                    </div>
                )}

                {/* Content */}
                <div className="flex gap-8">
                    {/* Sidebar - Departments */}
                    <aside className="hidden lg:block w-64 shrink-0">
                        <div className="cyber-card rounded-lg p-4 sticky top-24">
                            <h3 className="font-semibold mb-4 flex items-center gap-2">
                                <Folder className="w-4 h-4 text-neon-cyan" />
                                Departments
                            </h3>
                            <div className="space-y-1">
                                {departments?.map((dept) => (
                                    <Link
                                        key={dept.id}
                                        to={`/browse?department=${dept.id}`}
                                        className={`block px-3 py-2 rounded-md text-sm transition-colors ${
                                            departmentId === String(dept.id)
                                                ? 'bg-neon-cyan/10 text-neon-cyan'
                                                : 'hover:bg-muted'
                                        }`}
                                    >
                                        <span className="font-mono text-xs text-muted-foreground mr-2">{dept.code}</span>
                                        {dept.name}
                                    </Link>
                                ))}
                            </div>
                        </div>
                    </aside>

                    {/* Notes Grid */}
                    <div className="flex-1">
                        {isLoading ? (
                            <div className="flex items-center justify-center py-20">
                                <div className="animate-spin w-8 h-8 border-2 border-neon-cyan border-t-transparent rounded-full" />
                            </div>
                        ) : notesData?.content.length === 0 ? (
                            <div className="text-center py-20">
                                <BookOpen className="w-16 h-16 mx-auto mb-4 text-muted-foreground" />
                                <h3 className="text-lg font-semibold mb-2">No notes found</h3>
                                <p className="text-muted-foreground">
                                    Try adjusting your filters or be the first to upload notes!
                                </p>
                            </div>
                        ) : (
                            <>
                                <div className={viewMode === 'grid'
                                    ? 'grid md:grid-cols-2 xl:grid-cols-3 gap-6'
                                    : 'space-y-4'
                                }>
                                    {notesData?.content.map((note) => (
                                        <NoteCard key={note.id} note={note} variant={viewMode} />
                                    ))}
                                </div>

                                {/* Pagination */}
                                {notesData && notesData.totalPages > 1 && (
                                    <div className="flex justify-center items-center gap-2 mt-8">
                                        <button
                                            onClick={() => updateFilter('page', String(page - 1))}
                                            disabled={notesData.first}
                                            className="px-4 py-2 rounded-lg border border-border disabled:opacity-50 disabled:cursor-not-allowed hover:border-neon-cyan/50 transition-colors"
                                        >
                                            Previous
                                        </button>
                                        <span className="px-4 py-2 text-muted-foreground">
                      Page {page + 1} of {notesData.totalPages}
                    </span>
                                        <button
                                            onClick={() => updateFilter('page', String(page + 1))}
                                            disabled={notesData.last}
                                            className="px-4 py-2 rounded-lg border border-border disabled:opacity-50 disabled:cursor-not-allowed hover:border-neon-cyan/50 transition-colors"
                                        >
                                            Next
                                        </button>
                                    </div>
                                )}
                            </>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}
