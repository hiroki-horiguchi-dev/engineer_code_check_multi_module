# やったこと
- [ゆめみさんの技術課題](https://github.com/yumemi-inc/android-engineer-codecheck)をやってみた
- マルチモジュール(構成は以下)
- <img width="372" alt="image" src="https://github.com/user-attachments/assets/e701d035-8512-48ce-b867-105790fd175c">
- `MVVM + Repository + UseCase` パターン
- API 通信

# ライブラリ
- 通信系
    - Kotlin Coroutine
    - Retrofit
    - okHttp3
    - Kotlin serialization
- DI
    - Dagger Hilt
- UI
    - Jetpack Compose

# 株式会社ゆめみ Android エンジニアコードチェック課題

## 概要

本プロジェクトは株式会社ゆめみ（以下弊社）が、弊社に Android エンジニアを希望する方に出す課題のベースプロジェクトです。本課題が与えられた方は、下記の概要を詳しく読んだ上で課題を取り組んでください。

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="docs/app.gif" width="320">

### 環境

- IDE：Android Studio Ladybug | 2024.2.1 Patch 2
- Kotlin：2.0.20
- Java：17
- Gradle：8.9
- minSdk：24
- targetSdk：35

※ ライブラリの利用はオープンソースのものに限ります。
※ 環境は適宜更新してください。

### 動作

![Screenshot_20241029_183733](https://github.com/user-attachments/assets/0e02fddc-3360-4309-89af-328f05e0f7d1)

- とりあえず "Kotlin" でリポジトリ検索をかけ、検索結果を30件 LazyColumn で表示します
