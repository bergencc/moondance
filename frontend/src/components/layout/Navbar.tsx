import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Search, Upload, Menu, X, Sun, Moon, User, LogOut, BookOpen } from 'lucide-react';
import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useTheme } from '../../context/ThemeContext';

export default function Navbar() {
    const location = useLocation();
    const navigate = useNavigate();

    const { isAuthenticated, user, logout } = useAuth();
    const { theme, toggleTheme } = useTheme();

    const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
    const [userMenuOpen, setUserMenuOpen] = useState(false);
    const [searchQuery, setSearchQuery] = useState('');

    const navLinks = [
        { href: '/', label: 'Home' },
        { href: '/browse', label: 'Browse' },
        { href: '/search', label: 'Search' },
    ];

    const handleSearch = (e: React.FormEvent) => {
        e.preventDefault();

        if (searchQuery.trim()) {
            navigate(`/search?q=${encodeURIComponent(searchQuery)}`);
        }
    };

    return (
        <header className="sticky top-0 z-50 w-full border-b border-border/50 bg-background/95 backdrop-blur supports-backdrop-filter:bg-background/60">
            <div className="max-w-7xl mx-auto px-4 sm:px-6">
                <div className="flex h-16 items-center justify-between gap-4">
                    {/* Logo */}
                    <Link to="/" className="flex items-center gap-2 shrink-0">
                        <div className="relative w-8 h-8">
                            <div className="absolute inset-0 bg-linear-to-br from-neon-cyan to-neon-purple rounded" />
                            <BookOpen className="absolute inset-1 w-6 h-6 text-cyber-black" />
                        </div>
                        <span className="font-display font-bold text-xl tracking-wider hidden sm:block">
              <span className="text-neon-cyan">MOON</span>
              <span className="text-foreground">DANCE</span>
            </span>
                    </Link>

                    {/* Desktop Navigation */}
                    <nav className="hidden md:flex items-center gap-1">
                        {navLinks.map((link) => (
                            <Link
                                key={link.href}
                                to={link.href}
                                className={`px-4 py-2 text-sm font-medium transition-colors rounded ${
                                    location.pathname === link.href
                                        ? 'text-neon-cyan bg-neon-cyan/10'
                                        : 'text-muted-foreground hover:text-foreground hover:bg-muted'
                                }`}
                            >
                                {link.label}
                            </Link>
                        ))}
                    </nav>

                    {/* Search */}
                    <form onSubmit={handleSearch} className="hidden lg:flex flex-1 max-w-md mx-4">
                        <div className="relative w-full">
                            <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                            <input
                                type="search"
                                placeholder="Search notes..."
                                value={searchQuery}
                                onChange={(e) => setSearchQuery(e.target.value)}
                                className="cyber-input w-full pl-10 pr-4 py-2 rounded-lg bg-muted text-sm"
                            />
                        </div>
                    </form>

                    {/* Right side */}
                    <div className="flex items-center gap-2">
                        {/* Theme toggle */}
                        <button
                            onClick={toggleTheme}
                            className="p-2 text-muted-foreground hover:text-foreground rounded-lg hover:bg-muted transition-colors"
                        >
                            {theme === 'dark' ? <Sun className="w-5 h-5" /> : <Moon className="w-5 h-5" />}
                        </button>

                        {isAuthenticated ? (
                            <>
                                {/* Upload button */}
                                <Link
                                    to="/upload"
                                    className="hidden sm:flex items-center gap-2 px-4 py-2 border border-border rounded-lg hover:border-neon-cyan/50 transition-colors text-sm font-medium"
                                >
                                    <Upload className="w-4 h-4" />
                                    Upload
                                </Link>

                                {/* User menu */}
                                <div className="relative">
                                    <button
                                        onClick={() => setUserMenuOpen(!userMenuOpen)}
                                        className="flex items-center gap-2 p-1 rounded-lg hover:bg-muted transition-colors"
                                    >
                                        <div className="w-8 h-8 rounded-full bg-linear-to-br from-neon-cyan to-neon-magenta flex items-center justify-center">
                      <span className="text-sm font-bold text-cyber-black">
                        {user?.name?.charAt(0).toUpperCase()}
                      </span>
                                        </div>
                                    </button>

                                    {userMenuOpen && (
                                        <>
                                            <div
                                                className="fixed inset-0 z-40"
                                                onClick={() => setUserMenuOpen(false)}
                                            />
                                            <div className="absolute right-0 mt-2 w-56 rounded-lg border border-border bg-card shadow-lg z-50 animate-slideUp">
                                                <div className="p-3 border-b border-border">
                                                    <p className="font-medium text-sm">{user?.name}</p>
                                                    <p className="text-xs text-muted-foreground">{user?.email}</p>
                                                </div>
                                                <div className="p-1">
                                                    <Link
                                                        to="/profile"
                                                        onClick={() => setUserMenuOpen(false)}
                                                        className="flex items-center gap-2 px-3 py-2 text-sm hover:bg-muted rounded-lg"
                                                    >
                                                        <User className="w-4 h-4" />
                                                        Profile
                                                    </Link>
                                                    <button
                                                        onClick={() => {
                                                            logout();
                                                            setUserMenuOpen(false);
                                                            navigate('/');
                                                        }}
                                                        className="flex items-center gap-2 px-3 py-2 text-sm hover:bg-muted rounded-lg w-full text-left text-red-400"
                                                    >
                                                        <LogOut className="w-4 h-4" />
                                                        Logout
                                                    </button>
                                                </div>
                                            </div>
                                        </>
                                    )}
                                </div>
                            </>
                        ) : (
                            <div className="flex items-center gap-2">
                                <Link
                                    to="/login"
                                    className="px-4 py-2 text-sm font-medium text-muted-foreground hover:text-foreground transition-colors"
                                >
                                    Login
                                </Link>
                                <Link
                                    to="/register"
                                    className="px-4 py-2 text-sm font-medium bg-neon-cyan text-cyber-black rounded-lg hover:shadow-neon-cyan transition-all"
                                >
                                    Sign Up
                                </Link>
                            </div>
                        )}

                        {/* Mobile menu button */}
                        <button
                            className="md:hidden p-2 text-muted-foreground hover:text-foreground rounded-lg hover:bg-muted transition-colors"
                            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
                        >
                            {mobileMenuOpen ? <X className="w-5 h-5" /> : <Menu className="w-5 h-5" />}
                        </button>
                    </div>
                </div>

                {/* Mobile menu */}
                {mobileMenuOpen && (
                    <div className="md:hidden border-t border-border animate-slideUp">
                        <div className="py-4 space-y-2">
                            {/* Mobile search */}
                            <form onSubmit={handleSearch} className="px-2">
                                <div className="relative">
                                    <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                                    <input
                                        type="search"
                                        placeholder="Search..."
                                        value={searchQuery}
                                        onChange={(e) => setSearchQuery(e.target.value)}
                                        className="cyber-input w-full pl-10 pr-4 py-2 rounded-lg bg-muted text-sm"
                                    />
                                </div>
                            </form>

                            {navLinks.map((link) => (
                                <Link
                                    key={link.href}
                                    to={link.href}
                                    onClick={() => setMobileMenuOpen(false)}
                                    className={`block px-4 py-2 text-sm font-medium rounded-lg mx-2 ${
                                        location.pathname === link.href
                                            ? 'text-neon-cyan bg-neon-cyan/10'
                                            : 'text-muted-foreground hover:text-foreground hover:bg-muted'
                                    }`}
                                >
                                    {link.label}
                                </Link>
                            ))}

                            {isAuthenticated && (
                                <Link
                                    to="/upload"
                                    onClick={() => setMobileMenuOpen(false)}
                                    className="block px-4 py-2 text-sm font-medium text-muted-foreground hover:text-foreground hover:bg-muted rounded-lg mx-2"
                                >
                                    Upload Notes
                                </Link>
                            )}
                        </div>
                    </div>
                )}
            </div>
        </header>
    );
}
