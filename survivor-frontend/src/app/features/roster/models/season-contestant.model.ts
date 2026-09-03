import { TribeHistory } from "./tribe-history.model";

export interface SeasonContestant {
    appearanceId: number;
    name: string;
    hometown: string;
    occupation: string;
    wikiUrl: string;
    imageUrl: string;
    finalPlacement: number | null;
    status: string | null;
    currentTribeName: string;
    currentTribeColorHex: string;
    timeline: TribeHistory[];
    placementSummary: string;
    seasonId: string;
    contestantId: number;
    bio: string | null;
    eliminationDay: number | null;
}