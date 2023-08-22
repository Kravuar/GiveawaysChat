import Typography from "@mui/material/Typography";

export type OwnerInfoProps = {
    name: string
}

export default function OwnerInfo({name}: OwnerInfoProps) {
//     TODO: some small card with info like:
//      profile pic,
//      subscribers count,
//      subscribed count, total giveaways
//      subscribe/unsubscribe button
//      ...
    return (
        <Typography>{name}</Typography>
    );
}