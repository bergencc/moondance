import { useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { Search, X, FileText } from 'lucide-react';
import { api } from '@/services/api';
import NoteCard from '@/components/notes/NoteCard';
import { useAuth } from '@/context/AuthContext';
import type { Note, ApiResponse, PageResponse } from '@/types';

export default function SearchPage() {
    const [searchParams, setSearchParams] = useSearchParams();
    const [inputValue, setInputValue] = useState(searchParams.get('q') || '');
    const { user } = useAuth();
    const schoolId = user?.schoolId || 1;

    const query = searchParams.get('q') || '';
    const page = parseInt(searchParams.get('page') || '0');

    const { data: results, isLoading, isFetching } = useQuery({
        queryKey: ['search', schoolId, query, page],
        queryFn: async () => {
            if (!query) return null;

            const res = await api.get<ApiResponse<PageResponse<Note>>>(
                `/notes/search?schoolId=${schoolId}&query=${encodeURIComponent(query)}&page=${page}&size=12`
            );

            return res.data.data;
        },
        enabled: query.length > 0,
    });

    const handleSearch = (e: React.FormEvent) => {
        e.preventDefault();

        if (inputValue.trim()) {
            setSearchParams({ q: inputValue.trim(), page: '0' });
        }
    };

    const clearSearch = () => {
        setInputValue('');
        setSearchParams({});
    };

    const goToPage = (newPage: number) => {
        setSearchParams({ q: query, page: String(newPage) });
    };

    return (
        <div className="min-h-[calc(100vh-4rem)] py-8">
            <div className="max-w-5xl mx-auto px-6">
                {/* Search Header */}
                <div className="mb-12">
                    <h1 className="font-display text-3xl font-bold mb-6 text-center">
                        Search <span className="text-neon-cyan">Notes</span>
                    </h1>

                    <form onSubmit={handleSearch} className="relative max-w-2xl mx-auto">
                        <Search className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                        <input
                            type="text"
                            value={inputValue}
                            onChange={(e) => setInputValue(e.target.value)}
                            placeholder="Search by title, description, or content..."
                            className="cyber-input w-full pl-12 pr-24 py-4 rounded-lg bg-muted text-lg"
                        />
                        {inputValue && (
                            <button
                                type="button"
                                onClick={clearSearch}
                                className="absolute right-20 top-1/2 -translate-y-1/2 p-1 text-muted-foreground hover:text-foreground transition-colors"
                            >
                                <X className="w-5 h-5" />
                            </button>
                        )}
                        <button
                            type="submit"
                            className="absolute right-2 top-1/2 -translate-y-1/2 px-4 py-2 bg-neon-cyan text-cyber-black font-medium rounded-md hover:shadow-neon-cyan transition-all"
                        >
                            Search
                        </button>
                    </form>
                </div>

                {/* Results */}
                {query && (
                    <div className="mb-6 flex items-center justify-between">
                        <p className="text-muted-foreground">
                            {results?.totalElements || 0} results for "<span className="text-foreground">{query}</span>"
                        </p>
                        {(isLoading || isFetching) && (
                            <div className="animate-spin w-5 h-5 border-2 border-neon-cyan border-t-transparent rounded-full" />
                        )}
                    </div>
                )}

                {!query && !results && (
                    <div className="text-center py-20">
                        <Search className="w-16 h-16 mx-auto mb-4 text-muted-foreground" />
                        <h3 className="text-lg font-semibold mb-2">Start searching</h3>
                        <p className="text-muted-foreground">
                            Enter keywords to find notes, summaries, and study materials
                        </p>
                    </div>
                )}

                {query && results?.content.length === 0 && (
                    <div className="text-center py-20">
                        <FileText className="w-16 h-16 mx-auto mb-4 text-muted-foreground" />
                        <h3 className="text-lg font-semibold mb-2">No results found</h3>
                        <p className="text-muted-foreground">
                            Try different keywords or check your spelling
                        </p>
                    </div>
                )}

                {results && results.content.length > 0 && (
                    <>
                        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
                            {results.content.map((note) => (
                                <NoteCard key={note.id} note={note} />
                            ))}
                        </div>

                        {/* Pagination */}
                        {results.totalPages > 1 && (
                            <div className="flex justify-center items-center gap-2 mt-8">
                                <button
                                    onClick={() => goToPage(page - 1)}
                                    disabled={results.first}
                                    className="px-4 py-2 rounded-lg border border-border disabled:opacity-50 disabled:cursor-not-allowed hover:border-neon-cyan/50 transition-colors"
                                >
                                    Previous
                                </button>
                                <span className="px-4 py-2 text-muted-foreground">
                  Page {page + 1} of {results.totalPages}
                </span>
                                <button
                                    onClick={() => goToPage(page + 1)}
                                    disabled={results.last}
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
    );
}
