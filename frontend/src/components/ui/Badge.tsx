import * as React from 'react';
import { cva, type VariantProps } from 'class-variance-authority';
import { cn } from '@/utils';

const badgeVariants = cva(
    'inline-flex items-center rounded-sm px-2 py-0.5 text-xs font-medium transition-colors',
    {
        variants: {
            variant: {
                default: 'bg-neon-cyan/20 text-neon-cyan border border-neon-cyan/30',
                secondary: 'bg-neon-purple/20 text-neon-purple border border-neon-purple/30',
                destructive: 'bg-red-500/20 text-red-400 border border-red-500/30',
                outline: 'border border-border text-foreground',
                success: 'bg-neon-green/20 text-neon-green border border-neon-green/30',
                warning: 'bg-neon-yellow/20 text-neon-yellow border border-neon-yellow/30',
            },
        },
        defaultVariants: {
            variant: 'default',
        },
    }
);

export interface BadgeProps
    extends React.HTMLAttributes<HTMLDivElement>,
        VariantProps<typeof badgeVariants> {}

function Badge({ className, variant, ...props }: BadgeProps) {
    return (
        <div className={cn(badgeVariants({ variant }), className)} {...props} />
    );
}

export { Badge, badgeVariants };