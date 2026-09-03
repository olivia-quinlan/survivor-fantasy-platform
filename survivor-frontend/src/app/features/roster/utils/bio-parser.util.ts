const KNOWN_BIO_LABELS = [
  "Age:",
  "Hometown:",
  "Current Residence:",
  "Occupation:",
  "Current Occupation:",
  "3 Words to Describe You:",
  "Season:",
  "Seasons:",
  "Why do you want to be part of Survivor?",
  "What's one life experience you feel has prepared you for the game?",
  "Which previous player do you identify with the most? Who do you think you will play most like?",
  "What will you value in an alliance partner?",
  "Favorite Hobbies:",
  "Pet Peeves:",
  "What is the accomplishment you are most proud of?",
  "What is something we would never know from looking at you?",
  "Who in your life is your biggest inspiration and why?",
  "Why will you be the Sole Survivor?",
  "Why will you be the Sole Survivor:",
  "Why do you want to come back for Survivor 50 and how does it feel to be chosen for this milestone season?",
  "What one life experience since the last time you played do you feel has prepared you for 50?",
  "Coming into this season, what is the Survivor accomplishment you are most proud of? And what are you eager to have another shot at?",
  "What would you say is your Survivor reputation and how will you manage that perception in the game?",
  "What is your strategy going into 50? And how will it differ from previous time(s) you've played?",
  "Survivor fans have had the opportunity to help shape the game this season by voting on various elements – what are you hoping makes the cut and why?",
  "At this point in your life who is your biggest inspiration and why?",
].sort((a, b) => b.length - a.length);

export interface BioSegment {
  label: string | null;
  answer: string;
}

function normalize(text: string): string {
  return text.toLowerCase().replace(/[’‘]/g, "'");
}

export function parseBio(bio: string): BioSegment[] {
  const paragraphs = bio
    .split('\n\n')
    .map(paragraph => paragraph.trim())
    .filter(paragraph => paragraph.length > 0);

  return paragraphs.map(paragraph => {
    const normalizedParagraph = normalize(paragraph);
    const matchedLabel = KNOWN_BIO_LABELS.find(label => normalizedParagraph.startsWith(normalize(label)));

    if (!matchedLabel) {
      return { label: null, answer: paragraph };
    }

    return {
      label: paragraph.slice(0, matchedLabel.length),
      answer: paragraph.slice(matchedLabel.length).trim(),
    };
  });
}
