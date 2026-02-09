import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-hot-toast';
import { UserPlus, Mail, Lock, User, GraduationCap, Eye, EyeOff, Key } from 'lucide-react';
import { useAuth } from '@/context/AuthContext.tsx';

export default function RegisterPage() {
    const [formData, setFormData] = useState({
        email: '',
        password: '',
        confirmPassword: '',
        name: '',
        major: '',
        graduationYear: '',
        inviteCode: '',
    });
    const [showPassword, setShowPassword] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const { register } = useAuth();
    const navigate = useNavigate();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (formData.password !== formData.confirmPassword) {
            toast.error('Passwords do not match');

            return;
        }

        if (formData.password.length < 8) {
            toast.error('Password must be at least 8 characters');

            return;
        }

        setIsLoading(true);

        try {
            await register({
                email: formData.email,
                password: formData.password,
                name: formData.name,
                major: formData.major || undefined,
                graduationYear: formData.graduationYear ? parseInt(formData.graduationYear) : undefined,
                inviteCode: formData.inviteCode || undefined,
            });
            toast.success('Account created successfully!');
            navigate('/');
        } catch (error: any) {
            toast.error(error.response?.data?.message || 'Registration failed');
        } finally {
            setIsLoading(false);
        }
    };

    const currentYear = new Date().getFullYear();
    const years = Array.from({ length: 8 }, (_, i) => currentYear + i);

    return (
        <div className="min-h-[calc(100vh-4rem)] flex items-center justify-center py-12 px-4">
            <div className="absolute inset-0 cyber-grid opacity-30" />

            <div className="relative w-full max-w-md animate-slideUp">
                <div className="cyber-card rounded-lg p-8 border border-border/50 hover:border-neon-cyan/30 transition-colors">
                    {/* Header */}
                    <div className="text-center mb-8">
                        <div className="w-16 h-16 mx-auto mb-4 rounded-lg bg-neon-magenta/10 flex items-center justify-center">
                            <UserPlus className="w-8 h-8 text-neon-magenta" />
                        </div>
                        <h1 className="font-display text-2xl font-bold mb-2">Join Moon Dance</h1>
                        <p className="text-muted-foreground">Create your account to get started</p>
                    </div>

                    {/* Form */}
                    <form onSubmit={handleSubmit} className="space-y-5">
                        <div>
                            <label className="block text-sm font-medium mb-2">Full Name</label>
                            <div className="relative">
                                <User className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                                <input
                                    type="text"
                                    name="name"
                                    value={formData.name}
                                    onChange={handleChange}
                                    className="cyber-input w-full pl-10 pr-4 py-3 rounded-lg bg-muted"
                                    placeholder="John Doe"
                                    required
                                />
                            </div>
                        </div>

                        <div>
                            <label className="block text-sm font-medium mb-2">Bergen Email</label>
                            <div className="relative">
                                <Mail className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                                <input
                                    type="email"
                                    name="email"
                                    value={formData.email}
                                    onChange={handleChange}
                                    className="cyber-input w-full pl-10 pr-4 py-3 rounded-lg bg-muted"
                                    placeholder="you@bergen.edu"
                                    required
                                />
                            </div>
                            <p className="text-xs text-muted-foreground mt-1">Use your .edu email for automatic school matching</p>
                        </div>

                        <div className="grid grid-cols-2 gap-4">
                            <div>
                                <label className="block text-sm font-medium mb-2">Major</label>
                                <input
                                    type="text"
                                    name="major"
                                    value={formData.major}
                                    onChange={handleChange}
                                    className="cyber-input w-full px-4 py-3 rounded-lg bg-muted"
                                    placeholder="Computer Science"
                                />
                            </div>

                            <div>
                                <label className="block text-sm font-medium mb-2">Graduation</label>
                                <div className="relative">
                                    <GraduationCap className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                                    <select
                                        name="graduationYear"
                                        value={formData.graduationYear}
                                        onChange={handleChange}
                                        className="cyber-input w-full pl-10 pr-4 py-3 rounded-lg bg-muted appearance-none cursor-pointer"
                                    >
                                        <option value="">Year</option>
                                        {years.map((year) => (
                                            <option key={year} value={year}>{year}</option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div>
                            <label className="block text-sm font-medium mb-2">Password</label>
                            <div className="relative">
                                <Lock className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                                <input
                                    type={showPassword ? 'text' : 'password'}
                                    name="password"
                                    value={formData.password}
                                    onChange={handleChange}
                                    className="cyber-input w-full pl-10 pr-12 py-3 rounded-lg bg-muted"
                                    placeholder="••••••••"
                                    required
                                    minLength={8}
                                />
                                <button
                                    type="button"
                                    onClick={() => setShowPassword(!showPassword)}
                                    className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground transition-colors"
                                >
                                    {showPassword ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                                </button>
                            </div>
                        </div>

                        <div>
                            <label className="block text-sm font-medium mb-2">Confirm Password</label>
                            <div className="relative">
                                <Lock className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                                <input
                                    type={showPassword ? 'text' : 'password'}
                                    name="confirmPassword"
                                    value={formData.confirmPassword}
                                    onChange={handleChange}
                                    className="cyber-input w-full pl-10 pr-4 py-3 rounded-lg bg-muted"
                                    placeholder="••••••••"
                                    required
                                />
                            </div>
                        </div>

                        <div>
                            <label className="block text-sm font-medium mb-2">Invite Code (Optional)</label>
                            <div className="relative">
                                <Key className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                                <input
                                    type="text"
                                    name="inviteCode"
                                    value={formData.inviteCode}
                                    onChange={handleChange}
                                    className="cyber-input w-full pl-10 pr-4 py-3 rounded-lg bg-muted"
                                    placeholder="Enter invite code"
                                />
                            </div>
                            <p className="text-xs text-muted-foreground mt-1">Have an invite code? Enter it to join.</p>
                        </div>

                        <button
                            type="submit"
                            disabled={isLoading}
                            className="cyber-button w-full bg-neon-magenta text-white font-semibold py-3 rounded-lg hover:shadow-neon-magenta transition-all disabled:opacity-50 disabled:cursor-not-allowed"
                        >
                            {isLoading ? (
                                <span className="flex items-center justify-center gap-2">
                  <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin" />
                  Creating account...
                </span>
                            ) : (
                                'Create Account'
                            )}
                        </button>
                    </form>

                    {/* Footer */}
                    <div className="mt-6 text-center text-sm text-muted-foreground">
                        Already have an account?{' '}
                        <Link to="/login" className="text-neon-cyan hover:text-neon-cyan/80 transition-colors">
                            Sign in
                        </Link>
                    </div>
                </div>

                {/* Decorative elements */}
                <div className="absolute -top-4 -right-4 w-8 h-8 border-t-2 border-r-2 border-neon-magenta/50" />
                <div className="absolute -bottom-4 -left-4 w-8 h-8 border-b-2 border-l-2 border-neon-cyan/50" />
            </div>
        </div>
    );
}
