import React, {useState} from 'react';
import Giveaway, {GiveawayProps} from "./Giveaway";
import {useSubscription} from "react-stomp-hooks";
import {endpoints} from "../../config/Stomp";
import {parseMessage} from "../../services/UtilServices";
import {Container, Stack} from "@mui/material";

export default function GiveawaysContainer() {
    const [giveaways, setGiveaways] = useState<React.ReactElement<GiveawayProps>[]>([]);
    useSubscription(endpoints.historyInit, (message) => initContainer(parseMessage<GiveawayProps[]>(message)));
    useSubscription(endpoints.giveaways, (message) => handleNewGiveaway(parseMessage<GiveawayProps>(message)));

    function initContainer(giveaways: GiveawayProps[]) {
        setGiveaways(giveaways.map(giveawayProps => <Giveaway key={giveawayProps.id} {...giveawayProps}/>));
    }

    function handleNewGiveaway(newGiveaway: GiveawayProps) {
        setGiveaways((prev) => [...prev, <Giveaway key={newGiveaway.id} {...newGiveaway}/>]);
    }

    // TODO: Change container to some proper scrollable list
    return (
        <Container>
            {giveaways}
        </Container>
    );
};