package org.springframework.cloud.bus.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)	// for serializers
public class HelloRemoteApplicationEvent extends RemoteApplicationEvent {

	private String label;

	public HelloRemoteApplicationEvent(Object source, String originService, String destinationService, String label) {
		super(source, originService, destinationService);
		this.label = label;
	}
}
