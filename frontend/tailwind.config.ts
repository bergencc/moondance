/** @type {import('tailwindcss').Config} */
export default {
    darkMode: 'class',
    content: [
        './index.html',
        './src/**/*.{js,ts,jsx,tsx}',
    ],
    theme: {
        extend: {
            colors: {
                cyber: {
                    black: '#0a0a0f',
                    dark: '#12121a',
                    darker: '#0d0d14',
                    gray: '#1a1a24',
                    light: '#f0f0f5',
                    white: '#fafafa',
                },
                neon: {
                    cyan: '#00ffff',
                    magenta: '#ff00ff',
                    pink: '#ff1493',
                    purple: '#8b5cf6',
                    blue: '#3b82f6',
                    green: '#00ff88',
                    yellow: '#facc15',
                    orange: '#ff6b35',
                },
                accent: {
                    DEFAULT: 'rgb(var(--accent) / <alpha-value>)',
                    foreground: 'rgb(var(--accent-foreground) / <alpha-value>)',
                    primary: '#00ffff',
                    secondary: '#ff00ff',
                    tertiary: '#8b5cf6',
                },
                background: 'rgb(var(--background) / <alpha-value>)',
                foreground: 'rgb(var(--foreground) / <alpha-value>)',
                card: {
                    DEFAULT: 'rgb(var(--card) / <alpha-value>)',
                    foreground: 'rgb(var(--card-foreground) / <alpha-value>)',
                },
                popover: {
                    DEFAULT: 'rgb(var(--popover) / <alpha-value>)',
                    foreground: 'rgb(var(--popover-foreground) / <alpha-value>)',
                },
                primary: {
                    DEFAULT: 'rgb(var(--primary) / <alpha-value>)',
                    foreground: 'rgb(var(--primary-foreground) / <alpha-value>)',
                },
                secondary: {
                    DEFAULT: 'rgb(var(--secondary) / <alpha-value>)',
                    foreground: 'rgb(var(--secondary-foreground) / <alpha-value>)',
                },
                muted: {
                    DEFAULT: 'rgb(var(--muted) / <alpha-value>)',
                    foreground: 'rgb(var(--muted-foreground) / <alpha-value>)',
                },
                destructive: {
                    DEFAULT: 'rgb(var(--destructive) / <alpha-value>)',
                    foreground: 'rgb(var(--destructive-foreground) / <alpha-value>)',
                },
                border: 'rgb(var(--border) / <alpha-value>)',
                input: 'rgb(var(--input) / <alpha-value>)',
                ring: 'rgb(var(--ring) / <alpha-value>)',
            },
            fontFamily: {
                display: ['Orbitron', 'system-ui', 'sans-serif'],
                sans: ['Space Grotesk', 'system-ui', 'sans-serif'],
                mono: ['JetBrains Mono', 'monospace'],
            },
            boxShadow: {
                'neon-cyan': '0 0 5px #00ffff, 0 0 20px rgba(0, 255, 255, 0.3)',
                'neon-magenta': '0 0 5px #ff00ff, 0 0 20px rgba(255, 0, 255, 0.3)',
                'neon-purple': '0 0 5px #8b5cf6, 0 0 20px rgba(139, 92, 246, 0.3)',
                'neon-green': '0 0 5px #00ff88, 0 0 20px rgba(0, 255, 136, 0.3)',
                'glow': '0 0 40px rgba(0, 255, 255, 0.15)',
                'glow-lg': '0 0 60px rgba(0, 255, 255, 0.2)',
            },
            backgroundImage: {
                'grid-pattern': 'linear-gradient(rgba(0, 255, 255, 0.03) 1px, transparent 1px), linear-gradient(90deg, rgba(0, 255, 255, 0.03) 1px, transparent 1px)',
                'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
                'cyber-gradient': 'linear-gradient(135deg, rgba(0, 255, 255, 0.1) 0%, rgba(139, 92, 246, 0.1) 50%, rgba(255, 0, 255, 0.1) 100%)',
            },
            backgroundSize: {
                'grid': '50px 50px',
            },
            animation: {
                'glow-pulse': 'glow-pulse 2s ease-in-out infinite',
                'scan-line': 'scan-line 4s linear infinite',
                'float': 'float 6s ease-in-out infinite',
                'shimmer': 'shimmer 2s linear infinite',
            },
            keyframes: {
                'glow-pulse': {
                    '0%, 100%': { opacity: 1 },
                    '50%': { opacity: 0.5 },
                },
                'scan-line': {
                    '0%': { transform: 'translateY(-100%)' },
                    '100%': { transform: 'translateY(100vh)' },
                },
                'float': {
                    '0%, 100%': { transform: 'translateY(0)' },
                    '50%': { transform: 'translateY(-10px)' },
                },
                'shimmer': {
                    '0%': { backgroundPosition: '-200% 0' },
                    '100%': { backgroundPosition: '200% 0' },
                },
            },
            borderRadius: {
                'cyber': '2px',
            },
        },
    },
    plugins: [],
}
