# PracticalTest02

Neculai Andreea 341C2

C:\Users\Andreea\AppData\Local\Android\Sdk\platform-tools\adb.exe devices
avahi-browse -rca
sudo /etc/init.d/avahi-daemon restart
sudo /etc/init.d/dbus start

Ethernet adapter Ethernet 3:

   Connection-specific DNS Suffix  . :
   Link-local IPv6 Address . . . . . : fe80::ed7b:477:4bfd:1853%67
   IPv4 Address. . . . . . . . . . . : 192.168.251.9
   Subnet Mask . . . . . . . . . . . : 255.255.255.0
   Default Gateway . . . . . . . . . : 192.168.251.199
   
   C:\Users\ancam\AppData\Local\Android\Sdk\platform-tools\adb.exe start-server
   
   EX6
   
   configurare avahi-daemon
   sudo vim /etc/avahi/services/chat.service
			<?xml version="1.0" standalone='no'?>
			<!DOCTYPE service-group SYSTEM "avahi-service.dtd">
			<service-group>
			  <name>My desktop</name>
			  <service>
				<type>_myservice._tcp</type>
				<port>5555</port>
			  </service>
			</service-group>
	
	se restarteaza demonul
	sudo /etc/init.d/dbus start
	sudo /etc/init.d/avahi-daemon restart
   
   
   EX7 a) avahi-browse -rca