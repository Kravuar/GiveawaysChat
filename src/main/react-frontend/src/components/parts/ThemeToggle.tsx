import { useContext } from 'react';
import { ColorModeContext } from '../hoc/withTheme';
import { IconButton, SxProps, useTheme, Theme, Tooltip } from '@mui/material';
import { DarkMode, LightMode } from '@mui/icons-material';

type Props = {
    sx?: SxProps<Theme>;
}

export default function ModeToggle(props: Props) {
    const theme = useTheme();
    const colorMode = useContext(ColorModeContext);

    const dark = theme.palette.mode === "dark";
    return (
        <Tooltip title={dark ? "Светлая тема" : "Тёмная тема" }>
            <IconButton sx={props.sx} onClick={colorMode.toggleColorMode}>
                {dark ? <LightMode /> : <DarkMode />}
            </IconButton>
        </Tooltip>
    );
}