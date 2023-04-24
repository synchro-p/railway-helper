import * as PIXI from 'pixi.js';

const app = new PIXI.Application({ width: 640, height: 480 });
document.body.appendChild(app.view);

const blueBox = new PIXI.Graphics();
blueBox.beginFill(0x0000ff);
blueBox.drawRect(150, 150, 150, 150);
blueBox.interactive = true;
blueBox.cursor = 'pointer';
const onBlueBoxClick = (event) => {
    blueBox.x = Math.random() * 200;
    blueBox.y = Math.random() * 200;
};
blueBox.on('click', onBlueBoxClick);

app.stage.addChild(blueBox);
