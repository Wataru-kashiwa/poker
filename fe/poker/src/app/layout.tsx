// app/layout.tsx
import MaterialThemeProvider from './theme';

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ja">
      <body>
        <MaterialThemeProvider>
          {children}
        </MaterialThemeProvider>
      </body>
    </html>
  );
}
