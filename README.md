# 図形描画ソフト
大学の講義で作成した図形描画ソフトの実装
![Frame](https://github.com/shun-2002/DrawingTool/assets/155641575/a1c1bf3c-882a-4ad5-a9f3-3be65fc45ac6)

## このアプリでできること
図形の描画、保存、印刷、アニメーションなどが行える

### 操作方法
- Rectangle,Flower,Oval,Hendecagonal,Cloud ボタン: 描画する図形の形を選択、キャンバス内でクリックしたままドラッグで描画  
- Image ボタン: このボタンを押して、キャンバス内をクリックすることで画像を描画可能  
- Line Pattern,Line Width,Line Count,Drop Shadow,Animation,Fill Color,Line Color,Background Color の選択ボックス: 破線パターン、線の幅などのような、対応する属性を変化させる選択ボックス  
- BPM、FillColorTransparency、LineColorTransparency のスライダー: アニメーション時のBPM値、透明度などの変更を行うスライダー
- Select ボタン: キャンバス内の図形を選択、図形描画時のようにドラッグすることで複数選択可
- Delete ボタン: 選択した図形を削除
- Cut,Copy ボタン: 選択図形のカット、コピーの実行
- Paste ボタン: カットまたはコピーされた図形の複製
- Front ボタン: 選択図形の最前面への移動
- Play ボタン: アニメーションの再生
- Stop ボタン: アニメーションの停止
- 左上のメニューバー: キャンバスのセーブ、セーブしたキャンバスのロード、キャンバスの印刷、キャンバスの画像保存

### アニメーション機能
この実装は講義内でヒントが示されていた部分も多い。その中で、一番オリジナリティがあると思う機能がアニメーション機能である。

![AnimationDemoVideo](https://github.com/shun-2002/DrawingTool/assets/155641575/2e0b074b-e06b-4f82-ae70-b61d59228fea)

まず、オブジェクトの Animation という属性を On にする。 On にするには、二通りの方法がある。
- オブジェクトを選択し、Animation の選択ボックスを On に切り替える
- Aniation の選択ボックスの表示がOnとなった状態で、新たにオブジェクトを描画する

Play ボタンを押すと Animation が On のオブジェクトのアニメーションを開始する。  
Play 中にオブジェクトにマウスカーソルを当てると、スピンジャンプする。
Stop ボタンでアニメーションが止まる
