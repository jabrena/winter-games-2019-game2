package org.jab.microservices;

import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

@Slf4j
public class App {

	public static void main(String[] args) throws Exception {
		long begin = System.currentTimeMillis();
		int port = Optional.ofNullable(System.getenv("PORT")) //
				.map(Integer::parseInt) //
				.orElse(8080);

		HttpHandler httpHandler = RouterFunctions.toHttpHandler(App.routes(),
				HandlerStrategies.builder().build());
		HttpServer httpServer = HttpServer.create().host("0.0.0.0").port(port)
				.handle(new ReactorHttpHandlerAdapter(httpHandler));
		httpServer.bindUntilJavaShutdown(Duration.ofSeconds(3), disposableServer -> {
			long elapsed = System.currentTimeMillis() - begin;
			LOGGER.info("Started in {} seconds", elapsed / 1000.0);
		});
	}

	static RouterFunction<ServerResponse> routes() {
		return new HelloHandler().routes().and(new MessageHandler().routes());
	}
}
