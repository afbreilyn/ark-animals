import { StickyNote } from "../sticky-notes/sticky-note";

export interface Board {
  title: string,
  stickyNotes: StickyNote[],
}
