import { create } from 'zustand';
import { persist } from 'zustand/middleware';

type Theme = 'light' | 'dark';

interface ThemeState {
    theme: Theme;
    setTheme: (theme: Theme) => void;
    toggleTheme: () => void;
}

export const useThemeStore = create<ThemeState>()(
    persist(
        (set, get) => ({
            theme: 'dark',

            setTheme: (theme) => {
                set({ theme });
                updateDocumentClass(theme);
            },

            toggleTheme: () => {
                const newTheme = get().theme === 'dark' ? 'light' : 'dark';
                set({ theme: newTheme });
                updateDocumentClass(newTheme);
            },
        }),
        {
            name: 'moon-dance-theme',
            onRehydrateStorage: () => (state) => {
                if (state) {
                    updateDocumentClass(state.theme);
                }
            },
        }
    )
);

function updateDocumentClass(theme: Theme) {
    if (typeof document !== 'undefined') {
        document.documentElement.classList.remove('light', 'dark');
        document.documentElement.classList.add(theme);
    }
}

// Initialize theme on load
if (typeof window !== 'undefined') {
    const stored = localStorage.getItem('moon-dance-theme');

    if (stored) {
        try {
            const { state } = JSON.parse(stored);
            updateDocumentClass(state.theme || 'dark');
        } catch {
            updateDocumentClass('dark');
        }
    } else {
        updateDocumentClass('dark');
    }
}
