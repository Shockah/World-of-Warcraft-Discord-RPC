package pl.shockah.wowdiscordrpc.bin;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import javax.annotation.Nonnull;

import javafx.scene.image.Image;
import pl.shockah.wowdiscordrpc.image.BinaryImageHandler;
import pl.shockah.wowdiscordrpc.image.DefaultBinaryImageHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinaryImageTests {
	@Nonnull
	private static final String cupcakeIpsum = "Sugar plum chocolate cake candy cake sweet roll marshmallow tiramisu danish. Topping chocolate cake candy. Gummies sesame snaps sweet roll. Pie topping tootsie roll candy cake sugar plum. Cookie soufflé wafer dragée cotton candy jelly beans jelly beans cookie tootsie roll. Caramels toffee caramels dessert jelly-o toffee. Muffin pie toffee. Wafer gummies cake dragée marshmallow marshmallow marshmallow biscuit. Bear claw caramels brownie fruitcake cake jelly-o tootsie roll. Cake pastry tootsie roll cake dessert chocolate cake. Pastry jujubes pie bear claw cupcake caramels ice cream. Lollipop jujubes oat cake.\n" +
			"\n" +
			"Powder pie donut halvah tart jelly. Pie ice cream chupa chups pastry carrot cake. Carrot cake biscuit pastry danish. Cake powder pie chocolate cake cheesecake biscuit croissant. Brownie chupa chups cake tiramisu tootsie roll topping liquorice muffin. Chocolate soufflé brownie toffee. Chocolate danish gummi bears. Marshmallow marshmallow cupcake danish tootsie roll tiramisu. Chupa chups gingerbread gummies cake soufflé wafer. Cookie chocolate bar carrot cake oat cake cotton candy candy lollipop. Brownie gingerbread pie sesame snaps marzipan toffee marzipan apple pie cheesecake. Liquorice tart cotton candy candy tiramisu ice cream sugar plum lollipop.\n" +
			"\n" +
			"Tootsie roll sugar plum caramels jelly beans lemon drops jelly beans powder carrot cake. Bear claw sweet roll cake apple pie dragée sweet carrot cake sesame snaps. Jelly croissant danish. Tart sugar plum chocolate bar brownie carrot cake brownie. Candy oat cake fruitcake caramels brownie sweet roll sweet brownie tiramisu. Jelly jelly chocolate sugar plum muffin powder. Pie chocolate tootsie roll sweet topping marzipan gummi bears cake bonbon. Muffin tart halvah tiramisu. Tart topping lollipop dragée. Muffin jelly beans liquorice oat cake jelly beans bonbon jelly beans tart cheesecake. Danish dessert icing danish macaroon liquorice. Icing gummi bears wafer sweet roll lollipop jelly-o. Jelly-o dessert brownie jujubes tootsie roll chocolate cake donut lemon drops sweet. Caramels croissant wafer tiramisu biscuit toffee apple pie jelly sesame snaps.\n" +
			"\n" +
			"Tart cookie liquorice croissant. Oat cake cheesecake danish dragée sugar plum powder tart pie. Toffee jelly topping carrot cake cake pie toffee. Pudding cake carrot cake. Sesame snaps jelly beans marshmallow chocolate cake macaroon jelly toffee. Jelly-o wafer gingerbread caramels cookie. Pudding pastry marzipan bonbon chupa chups candy. Dragée sweet tart gingerbread marshmallow. Wafer candy sugar plum macaroon sugar plum chocolate fruitcake cake. Cheesecake bonbon candy canes jelly beans dragée halvah oat cake sesame snaps gummi bears. Cookie sugar plum lemon drops topping lollipop tiramisu cheesecake. Chupa chups soufflé cheesecake cake brownie chocolate bar oat cake.\n" +
			"\n" +
			"Caramels marshmallow dragée candy chocolate bar chupa chups icing. Chocolate cake cookie jelly beans gummi bears pudding sesame snaps. Tootsie roll sweet roll marshmallow chocolate cake carrot cake chocolate macaroon jelly-o. Caramels tiramisu chocolate bar cake powder topping. Caramels lollipop chupa chups donut halvah croissant chocolate jelly-o cupcake. Cake dragée dessert oat cake chupa chups pudding cupcake wafer. Halvah toffee apple pie. Brownie candy canes donut chocolate cake gummies tart muffin. Lemon drops fruitcake sweet roll muffin chupa chups topping donut macaroon oat cake. Chupa chups gummies carrot cake tiramisu topping gummi bears. Topping lollipop gingerbread. Danish bear claw jelly-o gummi bears cake. Macaroon pudding sugar plum marzipan jelly sugar plum halvah. Apple pie marzipan chupa chups cupcake chocolate bar.";

	@Nonnull
	private static final String iNeedU = "Fall Everything (x3)\n" +
			"흩어지네\n" +
			"Fall Everything (x3)\n" +
			"떨어지네\n" +
			"너 땜에 나 이렇게 망가져\n" +
			"그만할래 이제 너 안 가져\n" +
			"못하겠어 뭣 같아서\n" +
			"제발 핑계 같은 건 삼가줘\n" +
			"니가 나한테 이럼 안 돼\n" +
			"니가 한 모든 말은 안대\n" +
			"진실을 가리고 날 찢어\n" +
			"날 찍어 나 미쳐 다 싫어\n" +
			"전부 가져가 난 니가 그냥 미워\n" +
			"But you’re my everything\n" +
			"You’re my\n" +
			"Everything You’re my (X2)\n" +
			"제발 좀 꺼져 huh\n" +
			"미안해 I hate u\n" +
			"사랑해 I hate u\n" +
			"용서해\n" +
			"I need you girl\n" +
			"왜 혼자 사랑하고 혼자서만 이별해\n" +
			"I need you girl\n" +
			"왜 다칠 걸 알면서 자꾸 니가 필요해\n" +
			"I need you girl\n" +
			"넌 아름다워\n" +
			"I need you girl\n" +
			"너무 차가워\n" +
			"I need you girl (X4)\n" +
			"It goes round and round\n" +
			"나 왜 자꾸 돌아오지\n" +
			"I go down and down\n" +
			"이쯤 되면 내가 바보지\n" +
			"나 무슨 짓을 해봐도\n" +
			"어쩔 수가 없다고\n" +
			"분명 내 심장 내 마음 내 가슴인데\n" +
			"왜 말을 안 듣냐고\n" +
			"또 혼잣말하네 또 혼잣말하네\n" +
			"또 혼잣말하네 또 혼잣말하네\n" +
			"넌 아무 말 안 해\n" +
			"아 제발 내가 잘할게\n" +
			"하늘은 또 파랗게\n" +
			"하늘은 또 파랗게\n" +
			"하늘이 파래서 햇살이 빛나서\n" +
			"내 눈물이 더 잘 보이나 봐\n" +
			"왜 나는 너인지 왜 하필 너인지\n" +
			"왜 너를 떠날 수가 없는지\n" +
			"I need you girl\n" +
			"왜 혼자 사랑하고 혼자서만 이별해\n" +
			"I need you girl\n" +
			"왜 다칠 걸 알면서\n" +
			"자꾸 니가 필요해\n" +
			"I need you girl\n" +
			"넌 아름다워\n" +
			"I need you girl\n" +
			"너무 차가워\n" +
			"I need you girl (X4)\n" +
			"Girl 차라리 차라리 헤어지자고 해줘\n" +
			"Girl 사랑이 사랑이 아니었다고 해줘\n" +
			"내겐 그럴 용기가 없어\n" +
			"내게 마지막 선물을 줘\n" +
			"더는 돌아갈 수 없도록\n" +
			"I need you girl\n" +
			"왜 혼자 사랑하고 혼자서만 이별해\n" +
			"I need you girl\n" +
			"왜 다칠 걸 알면서\n" +
			"자꾸 니가 필요해\n" +
			"I need you girl\n" +
			"넌 아름다워\n" +
			"I need you girl\n" +
			"너무 차가워\n" +
			"I need you girl (X4)";

	@Test
	void singleBitTest() {
		BinaryImageHandler handler = new DefaultBinaryImageHandler();

		BitBuffer buffer = new BitBuffer();
		buffer.write(true);

		buffer.seekTo(0);
		Image image = handler.write(buffer);
		assertEquals(14 * 14, image.getWidth() * image.getHeight());

		buffer = handler.read(image);
		assertEquals(1, buffer.getSize());
		assertTrue(buffer.read());
	}

	@Test
	void uintTest() {
		BinaryImageHandler handler = new DefaultBinaryImageHandler();

		BitBuffer buffer = new BitBuffer();
		buffer.writeUInt(32, Integer.MAX_VALUE);
		buffer.writeUInt(14, 12345);
		buffer.writeUInt(18, 123456);

		buffer.seekTo(0);
		Image image = handler.write(buffer);
		assertEquals(14 * 14, image.getWidth() * image.getHeight());

		buffer = handler.read(image);
		assertEquals(32 + 14 + 18, buffer.getSize());
		assertEquals(Integer.MAX_VALUE, buffer.readUInt(32));
		assertEquals(12345, buffer.readUInt(14));
		assertEquals(123456, buffer.readUInt(18));
	}

	@Test
	void stringTest() {
		BinaryImageHandler handler = new DefaultBinaryImageHandler();

		BitBuffer buffer = new BitBuffer();
		buffer.writeString(16, cupcakeIpsum, Charset.forName("UTF-8"));
		buffer.writeString(16, iNeedU, Charset.forName("UTF-8"));

		buffer.seekTo(0);
		Image image = handler.write(buffer);
		assertEquals(51 * 51, image.getWidth() * image.getHeight());

		buffer = handler.read(image);
		assertEquals(cupcakeIpsum, buffer.readString(16, Charset.forName("UTF-8")));
		assertEquals(iNeedU, buffer.readString(16, Charset.forName("UTF-8")));
	}

	@Test
	void stringRedAndBlueTest() {
		BinaryImageHandler handler = new DefaultBinaryImageHandler(0, 0, 0, 8, 0, 8);

		BitBuffer buffer = new BitBuffer();
		buffer.writeString(16, cupcakeIpsum, Charset.forName("UTF-8"));
		buffer.writeString(16, iNeedU, Charset.forName("UTF-8"));

		buffer.seekTo(0);
		Image image = handler.write(buffer);
		assertEquals(62 * 62, image.getWidth() * image.getHeight());

		buffer = handler.read(image);
		assertEquals(cupcakeIpsum, buffer.readString(16, Charset.forName("UTF-8")));
		assertEquals(iNeedU, buffer.readString(16, Charset.forName("UTF-8")));
	}

	@Test
	void stringGreenOnlyTest() {
		BinaryImageHandler handler = new DefaultBinaryImageHandler(0, 0, 0, 0, 8, 0);

		BitBuffer buffer = new BitBuffer();
		buffer.writeString(16, cupcakeIpsum, Charset.forName("UTF-8"));
		buffer.writeString(16, iNeedU, Charset.forName("UTF-8"));

		buffer.seekTo(0);
		Image image = handler.write(buffer);
		assertEquals(86 * 86, image.getWidth() * image.getHeight());

		buffer = handler.read(image);
		assertEquals(cupcakeIpsum, buffer.readString(16, Charset.forName("UTF-8")));
		assertEquals(iNeedU, buffer.readString(16, Charset.forName("UTF-8")));
	}
}