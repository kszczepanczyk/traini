import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.traini.app',
  appName: 'traini',
  webDir: 'dist/traini',
  server: {
    androidScheme: 'https'
  }
};

export default config;
