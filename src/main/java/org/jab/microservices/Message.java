package org.jab.microservices;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

	public static Validator<Message> validator = ValidatorBuilder.of(Message.class)
			.constraint(Message::getText, "text", c -> c.notBlank().lessThanOrEqual(8))
			.build();

	private String text;

}
