import React, {useState} from 'react';
import Giveaway from "./Giveaway";
import {useSubscription} from "react-stomp-hooks";
import {endpoints} from "../../config/Stomp";

// TODO: fix Giveaway typing

export default function GiveawaysContainer() {
    const [giveaways, setGiveaways] = useState<Giveaway[]>([]);
    const initSubscription = useSubscription(endpoints.historyInit, initContainer);
    const giveawaysSubscription = useSubscription(endpoints.giveaways);

    // TODO: Type message object
    function initContainer(message: any) {

    }

    function handleNewCard(newCard: Giveaway) {
        setGiveaways((prevCards) => [...prevCards, newCard]);
    };

    return (
        <div style={{height: '400px', overflowY: 'auto'}}>
            {giveaways.map((card) =>
                <Giveaway key={card.id} {...card} />
            )}
        </div>
    );
};