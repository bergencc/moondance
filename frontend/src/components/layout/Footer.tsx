import { Link } from 'react-router-dom';
import { BookOpen, Github, Mail } from 'lucide-react';

export default function Footer() {
    const currentYear = new Date().getFullYear();

    const footerLinks = {
        platform: [
            { label: 'Find Notes', href: '/browse' },
            { label: 'Upload Content', href: '/upload' },
            { label: 'Courses', href: '/courses' },
        ],
        support: [
            { label: 'Help Center', href: '/help' },
            { label: 'Privacy Policy', href: '/privacy' },
            { label: 'Terms of Service', href: '/terms' },
            { label: 'Contact Us', href: '/contact' },
        ],
        resources: [
            { label: 'API Documentation', href: '/api-docs' },
            { label: 'Community', href: '/community' },
            { label: 'Blog', href: '/blog' },
        ],
    };

    return (
        <footer className="border-t border-border bg-card/50">
            <div className="container mx-auto px-4 py-12">
                <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
                    {/* Brand */}
                    <div className="space-y-4">
                        <Link to="/" className="flex items-center gap-2">
                            <div className="relative w-8 h-8">
                                <div className="absolute inset-0 bg-linear-to-br from-neon-cyan to-neon-purple rounded-sm" />
                                <BookOpen className="absolute inset-1 w-6 h-6 text-cyber-black" />
                            </div>
                            <span className="font-display font-bold text-lg tracking-wider">
                <span className="text-neon-cyan">MOON</span>
                <span className="text-foreground">DANCE</span>
              </span>
                        </Link>
                        <p className="text-sm text-muted-foreground">
                            The peer-to-peer knowledge hub for Bergen Community College students. Study smarter, together.
                        </p>
                        <div className="flex items-center gap-4">
                            <a
                                href="https://github.com/bergencc"
                                target="_blank"
                                rel="noopener noreferrer"
                                className="text-muted-foreground hover:text-neon-cyan transition-colors"
                            >
                                <Github className="w-5 h-5" />
                            </a>
                            <a
                                href="mailto:ghost@snabxo.com"
                                className="text-muted-foreground hover:text-neon-cyan transition-colors"
                            >
                                <Mail className="w-5 h-5" />
                            </a>
                        </div>
                    </div>

                    {/* Links */}
                    <div>
                        <h4 className="font-semibold text-sm uppercase tracking-wider mb-4 text-muted-foreground">
                            Platform
                        </h4>
                        <ul className="space-y-2">
                            {footerLinks.platform.map((link) => (
                                <li key={link.href}>
                                    <Link
                                        to={link.href}
                                        className="text-sm text-muted-foreground hover:text-neon-cyan transition-colors"
                                    >
                                        {link.label}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </div>

                    <div>
                        <h4 className="font-semibold text-sm uppercase tracking-wider mb-4 text-muted-foreground">
                            Support
                        </h4>
                        <ul className="space-y-2">
                            {footerLinks.support.map((link) => (
                                <li key={link.href}>
                                    <Link
                                        to={link.href}
                                        className="text-sm text-muted-foreground hover:text-neon-cyan transition-colors"
                                    >
                                        {link.label}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </div>

                    <div>
                        <h4 className="font-semibold text-sm uppercase tracking-wider mb-4 text-muted-foreground">
                            Resources
                        </h4>
                        <ul className="space-y-2">
                            {footerLinks.resources.map((link) => (
                                <li key={link.href}>
                                    <Link
                                        to={link.href}
                                        className="text-sm text-muted-foreground hover:text-neon-cyan transition-colors"
                                    >
                                        {link.label}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>

                <div className="mt-12 pt-8 border-t border-border flex flex-col sm:flex-row items-center justify-between gap-4">
                    <p className="text-sm text-muted-foreground">
                        © {currentYear} Moon Dance Platform. Designed for Bergen Community College.
                    </p>
                    <div className="flex items-center gap-6">
                        <span className="text-xs text-muted-foreground/60 font-mono">v1.0.0</span>
                    </div>
                </div>
            </div>
        </footer>
    );
}
