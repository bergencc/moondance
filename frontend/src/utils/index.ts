import { type ClassValue, clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';
import { NOTE_TYPE_COLORS, NOTE_TYPE_LABELS, type NoteType } from '@/types';

export function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs));
}

export function formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 B';

    const k = 1024;
    const sizes = ['B', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));

    return parseFloat((bytes / Math.pow(k, i)).toFixed(1)) + ' ' + sizes[i];
}

export function formatDate(dateString: string): string {
    const date = new Date(dateString);

    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
    });
}

export function formatRelativeTime(dateString: string): string {
    const date = new Date(dateString);
    const now = new Date();
    const diffInSeconds = Math.floor((now.getTime() - date.getTime()) / 1000);

    if (diffInSeconds < 60) return 'just now';
    if (diffInSeconds < 3600) return `${Math.floor(diffInSeconds / 60)}m ago`;
    if (diffInSeconds < 86400) return `${Math.floor(diffInSeconds / 3600)}h ago`;
    if (diffInSeconds < 604800) return `${Math.floor(diffInSeconds / 86400)}d ago`;

    return formatDate(dateString);
}

export function formatNumber(num: number): string {
    if (num >= 1000000) {
        return (num / 1000000).toFixed(1) + 'M';
    }

    if (num >= 1000) {
        return (num / 1000).toFixed(1) + 'k';
    }

    return num.toString();
}

export function getNoteTypeLabel(type: string): string {
    return NOTE_TYPE_LABELS[type as NoteType] ?? type;
}

export function getNoteTypeColor(type: string): string {
    return NOTE_TYPE_COLORS[type as NoteType] ?? NOTE_TYPE_COLORS.OTHER;
}

export function getFileTypeIcon(mimeType: string): string {
    if (mimeType.includes('pdf')) return '📄';

    if (mimeType.includes('image')) return '🖼️';

    if (mimeType.includes('word')) return '📝';

    if (mimeType.includes('powerpoint') || mimeType.includes('presentation')) return '📊';

    return '📁';
}

export function truncate(str: string, length: number): string {
    if (str.length <= length) return str;

    return str.slice(0, length) + '...';
}

export function getInitials(name: string): string {
    return name
        .split(' ')
        .map((n) => n[0])
        .join('')
        .toUpperCase()
        .slice(0, 2);
}

export function debounce<T extends (...args: unknown[]) => unknown>(
    func: T,
    wait: number
): (...args: Parameters<T>) => void {
    let timeout: ReturnType<typeof setTimeout>;

    return (...args: Parameters<T>) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => func(...args), wait);
    };
}
