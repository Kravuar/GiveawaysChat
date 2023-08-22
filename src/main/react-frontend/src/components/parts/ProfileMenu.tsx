import { Link } from 'react-router-dom';
import { Avatar, Divider, IconButton, ListItemIcon, Menu, MenuItem, Tooltip } from '@mui/material';
import React, { Fragment, useState } from 'react';
import { Login, Logout, PersonAdd } from '@mui/icons-material';

type UserProfile = {
  picture?: string;
  name: string;
};

export default function ProfileMenu() {
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);
    const handleClick = (event: React.MouseEvent<HTMLElement>) => setAnchorEl(event.currentTarget);
    const handleClose = () => setAnchorEl(null);

    // TODO: user fetching
    const userProfile: UserProfile = {
        name: "Bebr"
    };

    return (
        <Fragment>
            <Tooltip title="Профиль">
                <IconButton
                    onClick={handleClick}
                    size="small"
                    aria-controls={open ? 'account-menu' : undefined}
                    aria-haspopup="true"
                    aria-expanded={open ? 'true' : undefined}
                >
                    <Avatar src={userProfile?.picture} alt={userProfile?.name} />
                </IconButton>
            </Tooltip>
            <Menu
                anchorEl={anchorEl}
                id="account-menu"
                open={open}
                onClose={handleClose}
                onClick={handleClose}
            >
                {
                    userProfile
                        ?
                        [
                            <MenuItem key="profile" onClick={handleClose} to="/profile" component={Link}>
                                <Avatar />
                                Профиль
                            </MenuItem>,
                            <Divider key="divider" />,
                            <MenuItem key="logout" onClick={handleClose}>
                                <ListItemIcon>
                                    <Logout />
                                </ListItemIcon>
                                Выйти
                            </MenuItem>
                        ]
                        :
                        [
                            <MenuItem key="signin" onClick={handleClose} to="/auth/signin" component={Link}>
                                <ListItemIcon>
                                    <Login />
                                </ListItemIcon>
                                Войти
                            </MenuItem>,
                            <MenuItem key="signup" onClick={handleClose} to="/auth/signup" component={Link}>
                                <ListItemIcon>
                                    <PersonAdd />
                                </ListItemIcon>
                                Зарегистрироваться
                            </MenuItem>
                        ]
                }
            </Menu>
        </Fragment>
    );
}