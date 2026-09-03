export function buildTribeGradient(tribeColors: string[]): string {
    const width: number = 100 / tribeColors.length;
    const stops = tribeColors.slice().reverse().map((color, index) => {
      return `${color} ${index*width}% ${(index + 1) * width}%`
    });

    return `linear-gradient(-35deg, ${stops.join(', ')})`;
}