import {Link} from 'react-router-dom';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import ProfileComponent from "../parts/ProfileMenu";
import {AppBar, SxProps, Toolbar} from '@mui/material';
import {useRouteMatch} from "../../services/UtilServices";
import {CalendarMonth, Contacts, Home} from '@mui/icons-material';
import {Theme} from '@emotion/react';
import ModeToggle from "../parts/ThemeToggle";

type RoutingTabsProps = {
    sx?: SxProps<Theme>;
}

function RoutingTabs(props: RoutingTabsProps) {
    const routeMatch = useRouteMatch(["/appointment", "/about", "/"]);
    const currentTab = routeMatch?.pattern?.path;

    return (
        <Tabs sx={props.sx} value={currentTab ? currentTab : false}>
            <Tab iconPosition="start" icon={<Home/>} label="Главная" value="/" to="/" component={Link}/>
            <Tab iconPosition="start" icon={<Contacts/>} label="Обо мне" value="/about" to="/about" component={Link}/>
            <Tab iconPosition="start" icon={<CalendarMonth/>} label="Запись" value="/appointment" to="/appointment"
                 component={Link}/>
        </Tabs>
    );
};

export default function Navbar() {
    return (
        <AppBar sx={{mb: 8, boxShadow: 5}} elevation={0} color="transparent" position="static">
            <Toolbar>
                <RoutingTabs sx={{flexGrow: 1}}/>
                <ModeToggle/>
                <ProfileComponent/>
            </Toolbar>
        </AppBar>
    );
};