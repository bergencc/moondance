import * as React from 'react';
import { Slot } from '@radix-ui/react-slot';
import { cva, type VariantProps } from 'class-variance-authority';
import { cn } from '@/utils';

const buttonVariants = cva(
    'inline-flex items-center justify-center whitespace-nowrap text-sm font-medium transition-all duration-200 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50',
    {
        variants: {
            variant: {
                default:
                    'bg-neon-cyan text-cyber-black hover:bg-neon-cyan/90 hover:shadow-neon-cyan focus-visible:ring-neon-cyan',
                destructive:
                    'bg-red-500 text-white hover:bg-red-600 focus-visible:ring-red-500',
                outline:
                    'border border-neon-cyan/50 bg-transparent text-neon-cyan hover:bg-neon-cyan/10 hover:border-neon-cyan focus-visible:ring-neon-cyan',
                secondary:
                    'bg-neon-purple text-white hover:bg-neon-purple/90 hover:shadow-neon-purple focus-visible:ring-neon-purple',
                ghost:
                    'hover:bg-muted hover:text-foreground',
                link:
                    'text-neon-cyan underline-offset-4 hover:underline',
                cyber:
                    'cyber-button bg-cyber-gray border border-neon-cyan/30 text-neon-cyan hover:border-neon-cyan hover:shadow-neon-cyan',
            },
            size: {
                default: 'h-10 px-4 py-2',
                sm: 'h-8 px-3 text-xs',
                lg: 'h-12 px-8 text-base',
                icon: 'h-10 w-10',
            },
        },
        defaultVariants: {
            variant: 'default',
            size: 'default',
        },
    }
);

export interface ButtonProps
    extends React.ButtonHTMLAttributes<HTMLButtonElement>,
        VariantProps<typeof buttonVariants> {
    asChild?: boolean;
}

const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
    ({ className, variant, size, asChild = false, ...props }, ref) => {
        const Comp = asChild ? Slot : 'button';
        return (
            <Comp
                className={cn(buttonVariants({ variant, size, className }))}
                ref={ref}
                {...props}
            />
        );
    }
);
Button.displayName = 'Button';

export { Button, buttonVariants };
