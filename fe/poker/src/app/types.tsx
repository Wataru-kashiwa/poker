// app/types.ts
export interface Score {
    scoreId: number;
    userId: number;
    username: string;
    gameDate: string;
    score: number;
    updatedAt: string;
  }

// app/types.ts
export interface ScoreData {
    scoreId: number;
    userId: number;
    username: string;
    gameDate: string;   // "2023-10-04" など
    score: number;      // スコア点数
    updatedAt: string;
  }
  
  