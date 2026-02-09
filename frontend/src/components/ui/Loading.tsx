import { cn } from '@/utils';

interface LoadingSpinnerProps {
    size?: 'sm' | 'md' | 'lg';
    className?: string;
}

const sizeClasses = {
    sm: 'w-4 h-4',
    md: 'w-8 h-8',
    lg: 'w-12 h-12',
};

export function LoadingSpinner({ size = 'md', className }: LoadingSpinnerProps) {
    return (
        <div
            className={cn(
                'relative',
                sizeClasses[size],
                className
            )}
        >
            <div className="absolute inset-0 rounded-full border-2 border-neon-cyan/20" />
            <div className="absolute inset-0 rounded-full border-2 border-transparent border-t-neon-cyan animate-spin" />
        </div>
    );
}

export function LoadingPage() {
    return (
        <div className="flex min-h-100 items-center justify-center">
            <div className="flex flex-col items-center gap-4">
                <LoadingSpinner size="lg" />
                <p className="text-sm text-muted-foreground animate-pulse">Loading...</p>
            </div>
        </div>
    );
}

export function LoadingOverlay() {
    return (
        <div className="absolute inset-0 flex items-center justify-center bg-background/80 backdrop-blur-sm z-50">
            <LoadingSpinner size="lg" />
        </div>
    );
}
