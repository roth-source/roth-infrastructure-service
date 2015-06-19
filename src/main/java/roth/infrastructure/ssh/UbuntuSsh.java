package roth.infrastructure.ssh;

import java.util.LinkedList;

import roth.lib.net.ssh.SshConfig;

public class UbuntuSsh extends ServerSsh
{
	
	public UbuntuSsh(String host)
	{
		super(host);
	}

	public UbuntuSsh(SshConfig config)
	{
		super(config);
	}
	
	@Override
	public LinkedList<String> update()
	{
		String command = "apt-get -y update;";
		return exec(command);
	}
	
	@Override
	public LinkedList<String> install(String app)
	{
		String command = "apt-get -y install %s;";
		return exec(String.format(command, app));
	}
	
}
