import * as React from 'react';
import { cn } from '@/utils';

export interface InputProps
    extends React.InputHTMLAttributes<HTMLInputElement> {
    icon?: React.ReactNode;
}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
    ({ className, type, icon, ...props }, ref) => {
        return (
            <div className="relative">
                {icon && (
                    <div className="absolute left-3 top-1/2 -translate-y-1/2 text-muted-foreground">
                        {icon}
                    </div>
                )}
                <input
                    type={type}
                    className={cn(
                        'flex h-10 w-full rounded-sm border border-border bg-card px-3 py-2 text-sm transition-all',
                        'placeholder:text-muted-foreground',
                        'focus:outline-none focus:border-neon-cyan focus:ring-1 focus:ring-neon-cyan/50',
                        'disabled:cursor-not-allowed disabled:opacity-50',
                        icon && 'pl-10',
                        className
                    )}
                    ref={ref}
                    {...props}
                />
            </div>
        );
    }
);
Input.displayName = 'Input';

export { Input };