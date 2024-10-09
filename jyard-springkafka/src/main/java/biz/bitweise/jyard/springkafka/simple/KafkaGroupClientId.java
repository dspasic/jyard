/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.bitweise.jyard.springkafka.simple;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

/**
 * Test to observe the difference between setting the group id and client id
 *
 * <p>Setting up the group id is used to identify the consumer for each partition.
 *
 * <p>So each partition will have its own group with a number used as suffix.
 *
 * <p>Partition 1 would have groupId-0 assigned. The client id is the name for the real consumer. If
 * the clientId is not set the defined client id will be consumer-groupId-N, where the N is the
 * number of concurrent running consumers defined with {@link KafkaListener#concurrency()}. If the
 * clientPrefix is defined the defined client id will be clientPrefix-N.
 *
 * <p>The following will appear in the logs <code>
 * [Consumer clientId=prefix-1, groupId=dejan.records]</code>
 */
@SpringBootApplication
@Configuration
@EnableKafka
public class KafkaGroupClientId {

  public static void main(String[] args) {
    SpringApplication.run(KafkaGroupClientId.class, args);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    var cf = new ConcurrentKafkaListenerContainerFactory<String, String>();
    cf.setConsumerFactory(consumerFactory());
    cf.getContainerProperties().setMissingTopicsFatal(false);
    cf.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
    return cf;
  }

  private ConsumerFactory<String, String> consumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "dejan");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OffsetResetStrategy.EARLIEST.toString());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
  }

  /**
   * This will generate the following log entry: <code>
   * [Consumer clientId=consumer-dejan.records-N, groupId=dejan.records]</code>
   */
  @KafkaListener(topics = "dejan.records", groupId = "dejan.records", concurrency = "2")
  public void listen1(ConsumerRecords<String, String> records) {
    records.forEach(System.out::println);
  }

  /**
   * This will generate the following log entry: <code>
   * [Consumer clientId=clientIdPrefix-N, groupId=dejan.records]</code>
   */
  @KafkaListener(
      topics = "dejan.records",
      clientIdPrefix = "clientIdPrefix",
      groupId = "dejan.records",
      concurrency = "2")
  public void listen2(ConsumerRecords<String, String> records) {
    records.forEach(System.out::println);
  }

  /**
   * This will generate the following log entry: <code>
   * [Consumer clientId=idPrefix-0, groupId=dejan.records]</code>. My guess is that the id is used
   * for the Observability.
   */
  @KafkaListener(
      topics = "dejan.records",
      id = "dejan.id",
      clientIdPrefix = "idPrefix",
      groupId = "dejan.records",
      concurrency = "2")
  public void listen3(ConsumerRecords<String, String> records) {
    records.forEach(System.out::println);
  }
}
