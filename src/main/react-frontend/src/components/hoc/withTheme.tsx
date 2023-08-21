import React, { useState, useMemo, createContext } from 'react';
import { ThemeProvider } from '@emotion/react';
import { CssBaseline, PaletteMode, Theme } from '@mui/material';

export const ColorModeContext = createContext({ toggleColorMode: () => {} });

export interface WithThemeProps {
    themeProvider: (mode: PaletteMode) => Theme,
    defaultMode: PaletteMode
}

export default function withTheme(Component: React.ComponentType<any>, {themeProvider, defaultMode}: WithThemeProps) {
    return (props: React.ComponentProps<typeof Component>) => {
        const [mode, setMode] = useState<PaletteMode>(defaultMode);
        const colorMode = useMemo(
            () => ({
                toggleColorMode: () => setMode((prevMode: PaletteMode) => prevMode === 'light' ? 'dark' : 'light')
            }),
            [],
        );
        const theme = useMemo(() => themeProvider(mode), [mode]);

        return (
            <ColorModeContext.Provider value={colorMode}>
                <ThemeProvider theme={theme}>
                    <CssBaseline>
                        <Component {...props}/>
                    </CssBaseline>
                </ThemeProvider>
            </ColorModeContext.Provider>
        );
    };
}