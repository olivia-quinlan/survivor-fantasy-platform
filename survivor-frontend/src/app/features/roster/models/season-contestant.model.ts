import { TribeHistory } from "./tribe-history.model";

export interface SeasonContestant {
    appearanceId: number;
    name: string;
    hometown: string;
    occupation: string;
    wikiUrl: string;
    imageUrl: string;
    finalPlacement: number | null;
    status: string;
    currentTribeName: string;
    currentTribeColorHex: string;
    timeline: TribeHistory[];
    placementSummary: string;
}