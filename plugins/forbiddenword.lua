local time = os.time();

function plugin(message) 
	if(string.match(message, "purple"))then
		local response = os.time() - time;
		time = os.time();
		return response;
	end
	
	return nil;
end