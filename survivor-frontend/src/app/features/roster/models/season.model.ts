export interface Season {
    seasonId: string,
    seasonName: string,
    premiereDate: string,
    finaleDate: string | null,
    location: string,
    numberOfDays: number,
    castSize: number,
    tribeColors: string[]
}