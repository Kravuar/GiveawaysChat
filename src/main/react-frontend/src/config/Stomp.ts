export const socketURL = "ws://" + process.env.REACT_APP_BASE_URL + "/giveaways-ws";
export const endpoints = {
    giveaways: "/topic/giveaways",
    historyInit: "/app/giveaways"
}