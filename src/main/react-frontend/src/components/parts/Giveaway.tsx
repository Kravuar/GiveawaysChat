import React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';

interface GiveawayProps {
    title: string;
    id: string;
    owner: string;
    usagesLeft: number;
    cardValue: number;
    creationDate: Date;
    expirationDate: Date;
    isPrivate: boolean;
}

export default function Giveaway({title, id, owner, usagesLeft, cardValue, creationDate, expirationDate, isPrivate}: GiveawayProps) {
    return (
        <Card sx={{
            padding: 2,
            marginBottom: 2,
            width: '100%',
            transition: 'transform 0.2s',
            ...(isPrivate && {
                transform: 'scale(1.05)',
                border: '2px solid #FFD700'
            }),
        }}>
            <CardContent>
                <Typography variant="h6">{title}</Typography>
                <Typography variant="subtitle2">Card ID: {id}</Typography>
                <Typography variant="body1">Owner: {owner}</Typography>
                <Typography variant="body1">Usages Left: {usagesLeft}</Typography>
                <Typography variant="body1">Card Value: {cardValue}</Typography>
                <Typography variant="body2">Creation Date: {creationDate.toDateString()}</Typography>
                <Typography variant="body2">Expiration Date: {expirationDate.toDateString()}</Typography>
                <Button variant="contained" color="primary">Use</Button>
            </CardContent>
        </Card>
    );
};