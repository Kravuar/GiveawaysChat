import { PaletteMode, createTheme, lighten } from '@mui/material';

const getPaletteTheme = (mode: PaletteMode) => createTheme({
    palette: {
        mode: mode,
        primary: {
            main: 'rgb(169, 132, 181)',
        },
        secondary: {
            main: 'rgb(166, 195, 156)',
        }
    }
});

export const getTheme = (mode: PaletteMode) => {
    let paletteTheme = getPaletteTheme(mode);

    return createTheme(paletteTheme, {
        components: {
            MuiButtonBase: {
                styleOverrides: {
                    root: {
                        '&:hover': {
                            boxShadow: `inset 0px -2px 0px 0px ${lighten(paletteTheme.palette.primary.light, 0.5)}`
                        },
                    },
                },
            },
            MuiTypography: {
                defaultProps: {
                    color: paletteTheme.palette.text.secondary
                }
            }
        }
    });
};