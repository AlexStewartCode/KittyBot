local time = os.time();
function plugin(message) 
	if(message.match("purple"))then
		local response = os.time() - time;
		time = os.time();
		return response;
	end
	return nil;
end