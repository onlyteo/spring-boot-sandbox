import path from "path";
import webpack from "webpack";
import HtmlWebpackPlugin from "html-webpack-plugin";
import ForkTsCheckerWebpackPlugin from "fork-ts-checker-webpack-plugin";
import ESLintPlugin from "eslint-webpack-plugin";
import ESLintWebpackPlugin from "eslint-webpack-plugin";
import {TsconfigPathsPlugin} from "tsconfig-paths-webpack-plugin";

const webpackConfig = (env): {
    devtool: string;
    output: { path: string; filename: string };
    devServer: {
        proxy: { context: string[]; secure: boolean; target: string }[];
        static: string;
        historyApiFallback: boolean;
        port: number;
        compress: boolean;
        hot: boolean;
        open: boolean
    };
    entry: string;
    resolve: { extensions: string[]; plugins: TsconfigPathsPlugin[] };
    plugins: (HtmlWebpackPlugin | webpack.DefinePlugin | ForkTsCheckerWebpackPlugin | ESLintWebpackPlugin)[];
    module: {
        rules: ({ test: RegExp; loader: string } | {
            test: RegExp;
            loader: string;
            options: { transpileOnly: boolean };
            exclude: RegExp
        } | { test: RegExp; use: string[] } | { test: RegExp; use: string[]; enforce: string })[]
    }
} => ({
    entry: "./src/index.tsx",
    devtool: 'inline-source-map',
    resolve: {
        extensions: [".ts", ".tsx", ".js"],
        plugins: [new TsconfigPathsPlugin()]
    },
    output: {
        path: path.join(__dirname, "/dist"),
        filename: "build.js"
    },
    module: {
        rules: [
            {
                test: /\.(png|jpg|jpeg|gif|ico|svg)$/i,
                loader: "file-loader"
            },
            {
                test: /\.tsx?$/,
                options: {
                    transpileOnly: true
                },
                exclude: /dist/,
                loader: "ts-loader"
            },
            {
                test: /\.css$/i,
                use: ["style-loader", "css-loader"],
            },
            {
                test: /\.js$/,
                // exclude: /node_modules/, // This is the line that caused the problem. Remove or comment it out.
                enforce: "pre",
                use: ["source-map-loader"],
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            filename: "index.html",
            template: path.join(__dirname, "public", "index.html"),
            favicon: path.join(__dirname, "public", "favicon.ico")
        }),
        new webpack.DefinePlugin({
            "process.env.PRODUCTION": env.production || !env.development,
            "process.env.NAME": JSON.stringify(require("./package.json").name),
            "process.env.VERSION": JSON.stringify(require("./package.json").version)
        }),
        new ForkTsCheckerWebpackPlugin(),
        new ESLintPlugin({files: "./src/**/*.{ts,tsx,js,jsx}"})
    ],
    devServer: {
        port: 3000,
        static: path.join(__dirname, "dist"),
        hot: true,
        open: true,
        compress: true,
        historyApiFallback: true,
        proxy: [
            {
                context: ['/api', '/login', '/logout', '/oauth2', '/error', '/webjars', '/assets'],
                target: 'http://localhost:8080',
                secure: false
            }
        ]
    }
});

export default webpackConfig;