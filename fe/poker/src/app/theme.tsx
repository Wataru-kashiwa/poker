'use client';

import React from 'react';
import { createTheme, ThemeProvider } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      main: '#4285F4', // Googleの青色
    },
    secondary: {
      main: '#DB4437', // Googleの赤色
    },
  },
  typography: {
    fontFamily: [
      'Roboto',
      'Noto Sans JP',
      'Helvetica',
      'Arial',
      'sans-serif',
    ].join(','),
  },
});

export default function MaterialThemeProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  return <ThemeProvider theme={theme}>{children}</ThemeProvider>;
}
