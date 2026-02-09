import * as React from 'react';
import * as AvatarPrimitive from '@radix-ui/react-avatar';
import { cn, getInitials } from '@/utils';

const Avatar = React.forwardRef<
    React.ElementRef<typeof AvatarPrimitive.Root>,
    React.ComponentPropsWithoutRef<typeof AvatarPrimitive.Root>
>(({ className, ...props }, ref) => (
    <AvatarPrimitive.Root
        ref={ref}
        className={cn(
            'relative flex h-10 w-10 shrink-0 overflow-hidden rounded-sm',
            className
        )}
        {...props}
    />
));
Avatar.displayName = AvatarPrimitive.Root.displayName;

const AvatarImage = React.forwardRef<
    React.ElementRef<typeof AvatarPrimitive.Image>,
    React.ComponentPropsWithoutRef<typeof AvatarPrimitive.Image>
>(({ className, ...props }, ref) => (
    <AvatarPrimitive.Image
        ref={ref}
        className={cn('aspect-square h-full w-full', className)}
        {...props}
    />
));
AvatarImage.displayName = AvatarPrimitive.Image.displayName;

const AvatarFallback = React.forwardRef<
    React.ElementRef<typeof AvatarPrimitive.Fallback>,
    React.ComponentPropsWithoutRef<typeof AvatarPrimitive.Fallback>
>(({ className, ...props }, ref) => (
    <AvatarPrimitive.Fallback
        ref={ref}
        className={cn(
            'flex h-full w-full items-center justify-center bg-linear-to-br from-neon-cyan/20 to-neon-purple/20 text-sm font-medium text-foreground',
            className
        )}
        {...props}
    />
));
AvatarFallback.displayName = AvatarPrimitive.Fallback.displayName;

interface UserAvatarProps {
    name: string;
    avatarUrl?: string | null;
    className?: string;
    size?: 'sm' | 'md' | 'lg';
}

const sizeClasses = {
    sm: 'h-8 w-8 text-xs',
    md: 'h-10 w-10 text-sm',
    lg: 'h-14 w-14 text-base',
};

function UserAvatar({ name, avatarUrl, className, size = 'md' }: UserAvatarProps) {
    return (
        <Avatar className={cn(sizeClasses[size], className)}>
            <AvatarImage src={avatarUrl || undefined} alt={name} />
            <AvatarFallback>{getInitials(name)}</AvatarFallback>
        </Avatar>
    );
}

export { Avatar, AvatarImage, AvatarFallback, UserAvatar };
