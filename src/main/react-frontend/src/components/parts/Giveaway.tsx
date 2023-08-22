import React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import {Divider, Grid, Paper, Popover, Stack} from "@mui/material";
import OwnerInfo from "./OwnerInfo";

export interface GiveawayProps {
    title: string;
    id: string;
    owner: string;
    usagesLeft: number;
    value: number;
    creationDate: Date;
    expirationDate: Date;
    isPrivate: boolean;
}

// TODO: Add collected prop and make Collect button disabled when is true.
// TODO: Shitty colors and design

export default function Giveaway({title, id, owner, usagesLeft, value, creationDate, expirationDate, isPrivate}: GiveawayProps) {
    const [anchor, setAnchor] = React.useState<null | HTMLElement>(null);

    return (
        <Card sx={{
            padding: 2,
            marginBottom: 2,
            width: '100%',
            transition: 'transform 0.2s',
            ...(isPrivate && {
                transform: 'scale(1.05)',
                border: 5,
                borderColor: 'gold'
            }),
        }}>
            <CardContent>
                <Stack spacing={2}>
                    <Grid container direction="row" justifyContent="space-between" alignItems="center">
                        <Grid item>
                            <Button onClick={(event) => setAnchor(event.currentTarget)}>
                                {owner}
                            </Button>
                            <Popover
                                open={Boolean(anchor)}
                                anchorEl={anchor}
                                onClose={() => setAnchor(null)}
                                anchorOrigin={{
                                    vertical: 'bottom',
                                    horizontal: 'right',
                                }}
                            >
                                <OwnerInfo name={owner}/>
                            </Popover>
                        </Grid>
                        <Grid item>
                            <Typography variant="h6">{title}</Typography>
                        </Grid>
                        <Grid item>
                            <Typography variant="h6">#{id}</Typography>
                        </Grid>
                    </Grid>
                    <Divider/>
                    <Grid container direction="row" justifyContent="space-around" alignItems="center" spacing={2}>
                        <Grid item>
                            <Paper sx={{
                                px: 2,
                                backgroundColor: "secondary.light"
                            }}>
                                Дата создания: {creationDate.toUTCString()}
                            </Paper>
                        </Grid>
                        <Grid item>
                            <Paper sx={{
                                px: 2,
                                backgroundColor: "secondary.main"
                            }}>
                                Дата истечения: {expirationDate.toUTCString()}
                            </Paper>
                        </Grid>
                    </Grid>
                    <Divider/>
                    <Grid container direction="row" justifyContent="space-around" alignItems="center" spacing={2}>
                        <Grid item>
                            <Paper sx={{
                                px: 2,
                                backgroundColor: "primary.light"
                            }}>
                                Осталось использований: {usagesLeft}
                            </Paper>
                        </Grid>
                        <Grid item>
                            <Paper sx={{
                                px: 2,
                                backgroundColor: "primary.main"
                            }}>
                                Награда: {value}
                            </Paper>
                        </Grid>
                    </Grid>
                    <Divider/>
                    <Button>Получить награду</Button>
                </Stack>
            </CardContent>
        </Card>
    );
};